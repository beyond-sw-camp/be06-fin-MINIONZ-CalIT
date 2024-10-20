<script setup>
import { ref, onMounted } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useRoute } from 'vue-router';
import router from "@/router";
import { Notyf } from 'notyf';

const taskStore = useTaskStore();
const notyf = new Notyf();
const route = useRoute();
const workspaceId = route.params.workspaceId;

const errTitle = ref('');
const errContent = ref('');
const errLanguage = ref('');
const tasks = ref([]);
const selectedTask = ref(null);

const files = ref([]);

const handleFileChange = (event) => {
  files.value = Array.from(event.target.files);
};

const savePost = async () => {
  try {
    const formData = new FormData();

    formData.append('request', JSON.stringify({
      taskId: selectedTask.value,
      errboardTitle: errTitle.value,
      errboardContent: errContent.value,
      category: errLanguage.value
    }));

    if (files.value.length > 0) {
      files.value.forEach((file) => {
        formData.append('files', file);
      });
    }

    const response = await axiosInstance.post(`/api/errboard/write?workspaceId=${workspaceId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    console.log('게시글 저장 성공:', response.data);
    router.push(`/workspace/${workspaceId}/board/error/list`);
    notyf.success('게시글이 성공적으로 저장되었습니다.');
  } catch (error) {
    console.error('게시글 저장 중 오류가 발생했습니다:', error);
  }
};


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
  <div class="error-detail-container">
    <div class="error-note-container">
      <div class="error-input-wrap">
        <div class="error-title-container">
          <span class="column">
            <i class="error-title column-icon"></i>
            게시글 제목
          </span>
          <input v-model="errTitle" class="title-editor" placeholder="게시글 제목" />
        </div>

        <div class="language-section">
          <span class="column">
            <i class="languages column-icon"></i>
            언어 선택하기
          </span>
          <select v-model="errLanguage" class="title-editor">
            <option>JAVA</option>
            <option>C</option>
            <option>PYTHON</option>
            <option>JS</option>
            <option>SQL</option>
          </select>
        </div>

        <div class="language-section">
          <span class="column">
            <i class="languages column-icon"></i>
            태스크 연동하기
          </span>
          <select v-model="selectedTask" class="title-editor">
            <option v-for="task in tasks" :key="task.id" :value="task.id">
              {{ task.title }}
            </option>
          </select>
        </div>

        <div class="file-upload">
          <span class="column">
            <i class="languages column-icon"></i>
            파일 업로드
          </span>
          <input type="file" multiple @change="handleFileChange" />
        </div>

        <div class="content-section">
          <textarea v-model="errContent" placeholder="게시글 내용을 입력하세요..." class="content-editor"></textarea>
        </div>
      </div>
    </div>

    <button class="save-button" @click="savePost">저장하기</button>
  </div>
</template>

<style scoped>
.error-detail-container {
  padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}

.error-note-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}

.error-input-wrap {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.error-title-container {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.error-title {
  width: 24px;
  height: 24px;
  margin-right: 5px;
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

.languages {
  width: 24px;
  height: 24px;
  margin-right: 5px;
}

.language-section {
  display: flex;
  gap: 1rem;
  align-items: center;
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
