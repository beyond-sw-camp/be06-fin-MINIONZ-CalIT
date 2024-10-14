<script setup>
import { inject, ref, onMounted } from "vue";
import { useFriendsStore } from "@/stores/user/useFriendsStore";
import { useTaskStore } from "@/stores/scrum/useTaskStore";
import RighSideQaParticipants from "@/common/component/RightSide/RighSideQaParticipants.vue";
import { useRoute } from "vue-router";
import { axiosInstance } from '@/utils/axiosInstance';

const contentsTitle = inject("contentsTitle");
const contentsDescription = inject("contentsDescription");

contentsTitle.value = "QA 게시글 만들기";
contentsDescription.value = "QA 게시글을 만들어 보세요!";

const route = useRoute();
const workspaceId = route.params.workspaceId;
const friendsStore = useFriendsStore();
const taskStore = useTaskStore();
const selectedParticipants = friendsStore.selectedQAParticipants; 
const rightSideVisible = ref(false);
const activeComponentId = ref("participants-qa"); // QA 전용으로 설정
const selectedTask = ref(null);
const tasks = ref([]);

// 게시글 제목, 내용, 파일 업로드 관련 변수들
const qaTitle = ref('');
const qaContent = ref('');
const qaFiles = ref([]);  // 파일 업로드

// 파일 선택 핸들러
const handleFileChange = (event) => {
  qaFiles.value = Array.from(event.target.files);  // 파일 배열로 저장
};

// QA용 참여자 추가 함수
const addQaParticipant = (participants) => {
  friendsStore.selectedQaParticipants.value = participants; // 선택된 QA 참여자 반영
};

// RightSideComponent 열기
const rightSideOn = (componentId) => {
  activeComponentId.value = componentId;
  rightSideVisible.value = true;
};

// 게시글 저장 로직
// 게시글 저장 로직
const savePost = async () => {

  try {
    // 첫 번째 선택된 담당자의 ID만 workspaceParticipationId로 보냄
    const selectedParticipantId = selectedParticipants.value.length > 0 
      ? selectedParticipants.value[0].searchUserIdx  // searchUserIdx로 변경
      : null;

    const formData = new FormData();
    formData.append('request', JSON.stringify({
      qaboardTitle: qaTitle.value, // 게시글 제목
      qaboardContent: qaContent.value, // 게시글 내용
      taskId: selectedTask.value, // 선택된 태스크 ID
      workspaceParticipationId: selectedParticipantId, // 선택된 담당자 ID
    }));

    // 파일이 있을 때만 파일 추가
    if (qaFiles.value.length > 0) {
      qaFiles.value.forEach((file) => {
        formData.append('files', file);
      });
    }

    // 콘솔에 요청 데이터를 출력하여 확인
    console.log('보내는 데이터:', {
      qaboardTitle: qaTitle.value,
      qaboardContent: qaContent.value,
      taskId: selectedTask.value,
      workspaceParticipationId: selectedParticipantId,
      files: qaFiles.value,  // 추가된 파일
    });

    const response = await axiosInstance.post(`/qaboard/write?workspaceId=${workspaceId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data' // 자동으로 설정되지만 명시적으로 추가
      }
    });
    console.log('게시글 저장 성공:', response.data);
  } catch (error) {
    console.error('게시글 저장 중 오류 발생:', error);
  }
};

// 태스크 목록 불러오기
onMounted(async () => {
  try {
    const response = await taskStore.getWorkspaceTaskList(workspaceId);
    tasks.value = Array.isArray(response) ? response : [];
  } catch (error) {
    console.error('태스크 목록을 불러오는 중 오류가 발생했습니다.', error.message);
  }
});
</script>

<template>
  <div class="qa-wrap">
    <div class="qa-detail-container">
      <div class="qa-note-container">
        <div class="qa-input-wrap">
          <!-- 게시글 제목 -->
          <div class="qa-title-container">
            <span class="column">게시글 제목</span>
            <input v-model="qaTitle" class="title-editor" placeholder="게시글 제목" />
          </div>

          <!-- 담당자 추가 -->
          <div class="author-section">
            <div class="participants">
              <span class="column">담당자</span>
              <button class="issue-button" @click="rightSideOn('participants-qa')">담당자 추가하기</button>
              <!-- 담당자 목록 표시 -->
              <div class="users-list">
                <div v-for="participant in friendsStore.selectedQAParticipants" :key="participant.id">
                  <img :src="participant.persona" alt="" />
                  <span>{{ participant.userName }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- 태스크 연동 -->
          <div class="issue-section">
            <span class="column">태스크 연동하기</span>
            <select v-model="selectedTask" class="title-editor">
              <option v-for="task in tasks" :key="task.id" :value="task.id">
                {{ task.title }}
              </option>
            </select>
          </div>

          <!-- 글 작성 필드 -->
          <div class="qa-content-section">
            <label for="content">글 작성하기</label>
            <textarea v-model="qaContent" id="content" class="content-editor" placeholder="내용을 입력하세요..."></textarea>
          </div>

          <!-- 파일 업로드 -->
          <div class="file-upload">
            <label for="files">파일 업로드</label>
            <input type="file" id="files" multiple @change="handleFileChange" />
          </div>
        </div>
      </div>

      <!-- 저장 버튼 -->
   
      <button class="save-button" @click="savePost">저장하기</button>
    </div>

    <!-- QA 전용 RightSideComponent -->
    <RighSideQaParticipants v-show="rightSideVisible" :activeComponentId="activeComponentId" @update-qa-participants="addQaParticipant" />
  </div>
</template>

<style scoped>
.qa-wrap {
  display: flex;
  gap: 1rem;
  height: 70vh;
}
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
}
.qa-title {
  background-image: url("@/assets/icon/boardIcon/quillEditor.svg");
}
.title-editor {
  width: 70%;
  height: 2rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 0 10px;
  box-sizing: border-box;
}
.column {
  display: flex;
  align-items: center;
  width: 10rem;
  gap: 10px;
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
.participants {
  display: flex;
  align-items: center;
}
.participants img {
  border-radius: 50%;
  width: 30px;
  height: 30px;
}
.users-list {
  display: flex;
  flex-direction: column;
}
.user-profile {
  display: flex;
  gap: 10px;
  align-items: center;
}
.issue-button {
  background-color: #f8d7da;
  color: #c82333;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
}
.content-editor {
  width: 100%;
  height: 150px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
  font-size: 1rem;
  box-sizing: border-box;
}
</style>
