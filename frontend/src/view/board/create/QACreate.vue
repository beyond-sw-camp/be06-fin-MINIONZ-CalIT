<script setup>
import {inject} from "vue";
import QuillEditor from "@/common/component/Editor/QuillEditor.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 게시글 만들기';
contentsDescription.value = 'QA 게시글을 만들어 보세요!';
</script>

<template>
  <div class="qa-detail-container">
    <div class="qa-note-container">
      <div class="qa-input-wrap">
        <div class="qa-title-container">
        <span class="column">
          <i class="qa-title column-icon"></i>
          게시글 제목
        </span>
          <input  class="title-editor" placeholder="게시글 제목" />
        </div>
        <!--      설명 추가하기-->
        <div class="language-section">
        <span class="column">
          <i class="languages column-icon"></i>
          설명 추가하기
        </span>
          <input class="language-editor" placeholder="언어" />
        </div>
    <QuillEditor/>
  </div>
</div>
    <button class="save-button">저장하기</button>
</div>
</template>

<style scoped>
.qa-detail-container{
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
  //padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
  //height: 100%;
  //overflow-y: auto;
  //display: flex;
  //flex-direction: column-reverse;
}

.qa-input-wrap{
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.qa-title-container{
  display: flex;
  gap: 1rem;
  align-items: center;
}

.qa-title{
  background-image: url("@/assets/icon/boardIcon/quillEditor.svg");
  width: 24px;
  height: 24px;
  margin-right: 5px;
}

.title-editor {
  width: 100%;
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