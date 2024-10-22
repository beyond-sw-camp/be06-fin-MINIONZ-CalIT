<script setup>
import { ref, onMounted } from 'vue';
import { useFriendsStore } from '@/stores/user/useFriendsStore';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useRoute } from 'vue-router';
import { useQAStore } from '@/stores/board/useQAStore'; // QA Store 사용
import Multiselect from 'vue-multiselect';
import router from '@/router';
import 'vue-multiselect/dist/vue-multiselect.min.css'; // 멀티셀렉트 스타일

const taskStore = useTaskStore();
const qaStore = useQAStore(); // QA Store 사용
const route = useRoute();
const workspaceId = route.params.workspaceId;

// 게시글 제목, 내용, 태스크 선택, 파일 업로드 관련 변수들
const qaTitle = ref('');
const qaContent = ref('');
const selectedTask = ref(null);
const tasks = ref([]);
const qaFiles = ref([]); // 파일 업로드를 위한 ref

// 참여자 및 친구 목록 관리
const selectedParticipant = ref(null); // 단일 선택
const friendStore = useFriendsStore();
const filteredFriends = ref([]);

// 친구 목록을 가져오는 함수
const fetchFriends = async () => {
  try {
    await friendStore.getFriendsList(workspaceId);
    filteredFriends.value = friendStore.friends; // 친구 목록 저장
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

// 파일 선택 핸들러
const handleFileChange = (event) => {
  qaFiles.value = Array.from(event.target.files);
};

// 게시글 저장 로직 (파일 포함)
const savePost = async () => {
  try {
    // 참여자가 선택되지 않았을 경우 처리
    if (!selectedParticipant.value) {
      console.error('참여자가 선택되지 않았습니다.');
      return;
    }

    const formData = new FormData();

    // 게시글 데이터 추가 (JSON 형태로 전송)
    formData.append(
      'request',
      JSON.stringify({
        qaboardTitle: qaTitle.value,
        qaboardContent: qaContent.value,
        taskId: selectedTask.value,
        workspaceParticipationId: selectedParticipant.value.searchUserIdx, // 선택한 참여자 ID
      })
    );

    // 파일이 있을 경우에만 파일 추가
    if (qaFiles.value.length > 0) {
      qaFiles.value.forEach((file) => {
        formData.append('files', file);
      });
    }

    // QA 글 작성 API 호출
    await qaStore.writePost({
      workspaceId,
      taskId: selectedTask.value,
      qaboardTitle: qaTitle.value,
      qaboardContent: qaContent.value,
      workspaceParticipationId: selectedParticipant.value.searchUserIdx, // 선택한 참여자 ID 전송
      files: qaFiles.value, // 파일 배열
    });

    // 페이지 이동
    router.push(`/workspace/${workspaceId}/scrum/board/qa/list`);
  } catch (error) {
    console.error('게시글 저장 중 오류가 발생했습니다:', error);
  }
};

// 태스크 목록 불러오기
onMounted(async () => {
  try {
    const response = await taskStore.getWorkspaceTaskList(workspaceId);
    tasks.value = Array.isArray(response) ? response : [];
    await fetchFriends();
  } catch (error) {
    console.error(
      '태스크 목록을 불러오는 중 오류가 발생했습니다.',
      error.message
    );
  }
});
</script>

<template>
  <div class="qa-detail-container">
    <div class="qa-note-container">
      <div class="qa-input-wrap">
        <!-- 게시글 제목 입력 -->
        <div class="qa-title-container">
          <span class="column">게시글 제목</span>
          <input
            v-model="qaTitle"
            class="title-editor"
            placeholder="게시글 제목"
          />
        </div>

        <!-- 참여자 지정하기 -->
        <div class="author-section">
          <div class="participants">
            <span class="column">담당자</span>
            <multiselect
              v-model="selectedParticipant"
              :options="filteredFriends"
              :searchable="true"
              :close-on-select="true"
              :show-labels="false"
              placeholder="참여자를 선택하세요"
              label="userName"
              track-by="searchUserIdx"
            />
          </div>
        </div>

        <!-- 태스크 연동 -->
        <div class="task-section">
          <span class="column">태스크 연동하기</span>
          <select v-model="selectedTask" class="title-editor">
            <option v-for="task in tasks" :key="task.id" :value="task.id">
              {{ task.title }}
            </option>
          </select>
        </div>

        <!-- 파일 업로드 -->
        <div class="file-upload">
          <span class="column">파일 업로드</span>
          <input type="file" multiple @change="handleFileChange" />
        </div>

        <!-- 게시글 내용 입력 -->
        <div class="content-section">
          <textarea
            v-model="qaContent"
            placeholder="게시글 내용을 입력하세요..."
            class="content-editor"
          ></textarea>
        </div>
      </div>
    </div>

    <!-- 저장 버튼 -->
    <button class="save-button" @click="savePost">저장하기</button>
  </div>
</template>

<style scoped>
.qa-detail-container {
  padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}

.qa-note-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}

.qa-input-wrap {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.qa-title-container {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.title-editor {
  width: calc(100% - 160px);
  height: 2rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 0 10px;
}

.column {
  display: flex;
  align-items: center;
  width: 10rem;
  gap: 10px;
}

.file-upload {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.content-section {
  display: flex;
  flex-direction: column;
}

.content-editor {
  width: 100%;
  height: 150px;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
}

.save-button {
  background-color: #e0e8ff;
  color: #666daf;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 150px;
  margin-left: auto;
}
</style>
