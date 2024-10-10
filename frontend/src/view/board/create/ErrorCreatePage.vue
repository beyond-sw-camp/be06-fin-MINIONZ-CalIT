<script setup>
import { inject, ref } from "vue";
import { useErrorStore } from "@/stores/board/useErrorStore";
import QuillEditor from "@/common/component/Editor/QuillEditor.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Error 게시글 쓰기';
contentsDescription.value = 'Error 게시글을 만들어 보세요!';

const errorStore = useErrorStore();
const errTitle = ref('');
const errContent = ref({ html: '', text: '' });
const errLanguage = ref('');
const tasks = ref([]);
const selectedTask = ref(null);

const savePost = () => {
  errorStore.writePost({
    errboardTitle: errTitle.value,
    errboardContent: errContent.value.html,
    category: errLanguage.value,
    taskId: selectedTask.value,
  });
};
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
          <select class="title-editor">
            <option>Python</option>
            <option>Java</option>
            <option>JavaScript</option>
            <option>HTML</option>
            <option>CSS</option>
          </select>
        </div>
        <div class="language-section">
          <span class="column">
            <i class="languages column-icon"></i>
            태스크 연동하기
          </span>
          <select v-model="selectedTask" class="title-editor">
            <option v-for="task in tasks" :key="task.id" :value="task.id">{{ task.name }}</option>
          </select>
        </div>
        <QuillEditor v-model:html="errContent.html" v-model:text="errContent.text"/>
      </div>
    </div>
    <button class="save-button" @click="savePost">저장하기</button>
  </div>
</template>

<style scoped>
.error-detail-container{
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
  //padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}

.error-input-wrap{
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.error-title-container{
  display: flex;
  gap: 1rem;
  align-items: center;
}

.error-title{
  background-image: url("@/assets/icon/boardIcon/quillEditor.svg");
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

.column{
  display: flex;
  align-items: center;
  width: 10rem;
  gap: 10px;
}

.languages{
  background-image: url("@/assets/icon/boardIcon/quillDescription.svg");
  width: 24px;
  height: 24px;
  margin-right: 5px;
}

.language-section{
  display: flex;
  gap: 1rem;
  align-items: center;
}

.language-editor {
  width: 100%;
  height: 2rem;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 0 10px;
}

.save-button {
  background-color: #e0e8ff;
  color: #666daf;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  //margin-top: 20px;
  width: 150px;
  margin-left: auto;
}
</style>