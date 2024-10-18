<script setup>
import { inject, ref, onMounted } from 'vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useQAStore } from '@/stores/board/useQAStore';
import { useRoute } from 'vue-router';
import Multiselect from 'vue-multiselect';
import { useFriendsStore } from '@/stores/user/useFriendsStore';
import router from '@/router';
import { Notyf } from 'notyf';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 게시글 만들기';
contentsDescription.value = 'QA 게시글을 만들어 보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;
const notyf = new Notyf();

const taskStore = useTaskStore();
const qaStore = useQAStore();

const selectedParticipants = ref([]);
const selectedTask = ref(null);
const tasks = ref([]);

const qaTitle = ref('');
const qaContent = ref('');
const qaFiles = ref([]);
const friendStore = useFriendsStore();
const participants = ref([]);
const filteredFriends = ref([]);

const searchFriends = async () => {
  try {
    await friendStore.getFriendsList(workspaceId);
    filteredFriends.value = friendStore.friends;
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

const deleteParticipant = (searchUserIdx) => {
  participants.value = participants.value.filter(
    (participant) => participant.searchUserIdx !== searchUserIdx
  );
};

const handleFileChange = (event) => {
  qaFiles.value = Array.from(event.target.files);
};

const savePost = async () => {
  try {
    const formData = new FormData();

    await qaStore.writePost({
      workspaceId,
      taskId: selectedTask.value,
      qaboardTitle: qaTitle.value,
      qaboardContent: qaContent.value,
      workspaceParticipationId: 3,
      formData,
    });
    notyf.success('게시글이 성공적으로 저장되었습니다.');
    router.push(`/workspace/${workspaceId}/scrum/board/qa/list`);
  } catch (error) {
    console.error('게시글 저장 중 오류 발생:', error);
    notyf.error('게시글 저장 중 오류가 발생했습니다.');
  }
};

onMounted(async () => {
  try {
    const response = await taskStore.getWorkspaceTaskList(workspaceId);
    tasks.value = Array.isArray(response) ? response : [];
    await searchFriends();
  } catch (error) {
    console.error(
      '태스크 목록을 불러오는 중 오류가 발생했습니다.',
      error.message
    );
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
            <input
              v-model="qaTitle"
              class="title-editor"
              placeholder="게시글 제목"
            />
          </div>

          <div class="author-section">
            <div class="participants">
              <span class="column">담당자</span>
              <multiselect
                v-model="selectedParticipants"
                :options="filteredFriends"
                :searchable="true"
                :close-on-select="true"
                :show-labels="false"
                placeholder="참여자를 선택하세요"
                label="userName"
                track-by="searchUserIdx"
              />
              <div class="users-list">
                <div
                  class="selections participants"
                  v-if="participants && participants.length"
                >
                  <span
                    class="item"
                    v-for="participant in participants"
                    :key="participant.searchUserIdx"
                  >
                    {{ participant.userName }}
                    <span
                      @click="deleteParticipant(participant.searchUserIdx)"
                      style="cursor: pointer; margin: 0 10px; padding: 0"
                      >x</span
                    >
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div class="issue-section">
            <span class="column">태스크 연동하기</span>
            <select v-model="selectedTask" class="title-editor">
              <option v-for="task in tasks" :key="task.id" :value="task.id">
                {{ task.title }}
              </option>
            </select>
          </div>

          <div class="qa-content-section">
            <label for="content">글 작성하기</label>
            <textarea
              v-model="qaContent"
              id="content"
              class="content-editor"
              placeholder="내용을 입력하세요..."
            ></textarea>
          </div>

          <div class="file-upload">
            <label for="files">파일 업로드</label>
            <input type="file" id="files" multiple @change="handleFileChange" />
          </div>
        </div>
      </div>

      <button class="save-button" @click="savePost">저장하기</button>
    </div>
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
  background-image: url('@/assets/icon/boardIcon/quillEditor.svg');
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
