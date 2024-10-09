<script setup>
import {inject, ref} from "vue";
import { useQAStore  } from "@/stores/board/useQAStore";
import RightSideComponent from "@/common/component/RightSide/RightSideComponent.vue";
import QuillEditor from "@/common/component/Editor/QuillEditorMeeting.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 게시글 만들기';
contentsDescription.value = 'QA 게시글을 만들어 보세요!';

const qaStore = useQAStore();
const rightSideVisible = ref(false);
const activeComponentId = ref('');
const participants = ref([]);

const rightSideOn = (componentId) => {
  activeComponentId.value = componentId;
  rightSideVisible.value = true;
};
</script>

<template>
  <div class="qa-wrap">
    <div class="qa-detail-container">
      <div class="qa-note-container">
        <div class="qa-input-wrap">
          <div class="qa-title-container">
            <span class="column">
              <i class="qa-title column-icon"></i>
              게시글 제목
            </span>
            <input v-model="qaStore.title" class="title-editor" placeholder="게시글 제목" />
          </div>
          <!--      담당자 추가  -->
          <div class="author-section">
            <div class="participants">
              <span class="column">
                <i class="user-multiple column-icon"></i>
                담당자
              </span>
              <button class="issue-button" @click="rightSideOn('participants')">담당자 추가하기</button>
              <div class="users-list">
                <div class="user-profile" v-for="participant in participants" :key="participant.id">
                  <img :src="participant.persona" alt="참여자">
                  <span>{{ participant.username }}</span>
                </div>
              </div>
            </div>
          </div>
          <!--        태스크 추가하기-->
          <div class="issue-section">
            <span class="column">
              <i class="task-add column-icon"></i>
              태스크 추가하기
            </span>
            <button class="issue-button" @click="rightSideOn('task'); qaStore.updateTaskId('newTaskId')">태스크 연동하기</button>
            <span class="issue-id">{{ qaStore.taskId }}</span>
          </div>
          <QuillEditor/>
        </div>
      </div>
      <button class="save-button">저장하기</button>
    </div>
    <RightSideComponent v-show="rightSideVisible" :activeComponentId="activeComponentId"/>
  </div>
</template>

<style scoped>
.qa-wrap{
  display: flex;
  gap: 1rem;
  height: 70vh;
}

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
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}

.qa-input-wrap{
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.qa-title-container{
  display: flex;
}

.qa-title{
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
  width: 150px;
  margin-left: auto;
}

.author-section {
  display: flex;
  flex-direction: column;
}

.issue-section{
  display: flex;
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

.user-multiple {
  background-image: url("@/assets/icon/boardIcon/userMultiple.svg");
}

.task-add{
  background-image: url("@/assets/icon/boardIcon/taskAdd.svg");
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

.issue-id{
  color: #28303F;
  background-color: #F3F6FF;
  padding: 5px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.column-icon {
  background-size: cover;
  width: 24px;
  height: 24px;
  display: block;
}

.user-profile{
  display: flex;
  gap: 10px;
  align-items: center;
}
</style>