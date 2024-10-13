<script setup>
import { ref, onMounted } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';

import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useRoute } from 'vue-router';


const taskStore = useTaskStore();
const route = useRoute();
const workspaceId = route.params.workspaceId;

// 게시글 제목, 내용, 언어 선택, 파일 업로드 관련 변수들
const errTitle = ref('');
const errContent = ref('');  // 게시글 내용은 그냥 텍스트로 처리
const errLanguage = ref('');
const tasks = ref([]);
const selectedTask = ref(null);

// 파일 업로드를 위한 ref
const files = ref([]);

// 파일 선택 핸들러
const handleFileChange = (event) => {
  files.value = Array.from(event.target.files);
};

// 게시글 저장 로직
const savePost = async () => {
  try {
    const formData = new FormData();

    // JSON 데이터를 문자열로 변환하여 FormData에 추가
    formData.append('request', JSON.stringify({
      taskId: selectedTask.value,
      errboardTitle: errTitle.value,
      errboardContent: errContent.value,
      category: errLanguage.value
    }));

    // 파일이 있을 때만 파일 추가
    if (files.value.length > 0) {
      files.value.forEach((file) => {
        formData.append('files', file);
      });
    }

    // 요청 전송 - Content-Type을 설정하지 않음
    const response = await axiosInstance.post(`/api/errboard/write?workspaceId=${workspaceId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'  // 이 부분은 자동으로 설정되지만 명시적으로 추가해봅니다
      }
    });

    console.log('게시글 저장 성공:', response.data);
  } catch (error) {
    console.error('게시글 저장 중 오류가 발생했습니다:', error);
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
