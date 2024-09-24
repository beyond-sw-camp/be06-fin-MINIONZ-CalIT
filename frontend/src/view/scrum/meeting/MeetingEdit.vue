<script setup>
import {useRoute} from 'vue-router';
import {computed, inject, ref} from 'vue';
import {workspaceData} from '@/static/workspaceData';
import RightSideComponent from "@/view/scrum/meeting/component/RightSide/RightSideComponent.vue";
import QuillEditor from "@/common/component/Editor/QuillEditor.vue";

import user1 from '@/assets/icon/persona/user1.svg';
import user2 from '@/assets/icon/persona/user2.svg';
import user3 from '@/assets/icon/persona/user3.svg';

const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = workspace.value ? `${workspace.value.workspaceName} Meeting` : 'Meeting Detail';
contentsDescription.value = '회의를 수정하세요!';

const meetingTitle = ref('회의록 제목');
const meetingDescription = ref('회의록 상세 설명');
const startTime = ref('');
const endTime = ref('');
const rightSideVisible = ref(false);
const activeComponentId = ref('');
const editor = ref(null);
const isQuillVisible = ref(false);

const addNote = () => {
  isQuillVisible.value = true;
};
const saveNote = () => {
  // const noteContent = editor.value.querySelector('.ql-editor').innerHTML;
  console.log('Meeting title:', meetingTitle.value);
  // TODO 백엔드로 전송하거나 로컬 저장소에 저장하는 로직을 추가
};

const rightSideOn = (id) => {
  const meetingNoteContainer = document.querySelector('.meeting-note-container');
  if (meetingNoteContainer) {
    meetingNoteContainer.style.transition = 'width 0.5s ease';
    meetingNoteContainer.style.width = rightSideVisible.value ? '100%' : 'calc(100% - 300px)';
  }
  console.log('Add issue button clicked');
  activeComponentId.value = id;
  rightSideVisible.value = !rightSideVisible.value;
};
</script>

<template>
  <div class="meeting-wrap">
    <div class="meeting-note-container">
      <div class="meeting-input-wrap">
        <div class="meeting-title-container">
        <span class="column">
          <i class="meeting-title column-icon"></i>
          회의록 제목
        </span>
          <input v-model="meetingTitle" class="title-editor" placeholder="회의록 제목"/>
        </div>
        <!--      설명 추가하기-->
        <div class="issue-section">
        <span class="column">
          <i class="meeting-description column-icon"></i>
          설명 추가하기
        </span>
          <input v-model="meetingDescription" class="description-editor" placeholder="회의록 상세"/>
        </div>

        <!--     시간 추가하기-->
        <div class="issue-section">
        <span class="column">
          <i class="meeting-time column-icon"></i>
          회의 시간
        </span>
          <div class="meeting-time-input">
            <span>시작 시간</span>
            <input v-model="startTime" type="datetime-local" class="time-editor"/>
            <span>~ 종료 시간</span>
            <input v-model="endTime" type="datetime-local" class="time-editor"/>
          </div>

        </div>
        <!-- 작성자 및 회의 참가자 표시 -->
        <div class="author-section">
          <div class="author">
          <span class="column">
            <i class="user-editor column-icon"></i>
            작성자
          </span>
            <div class="user-profile">
              <img :src="user1" alt="작성자">
              <span>최승은</span>
            </div>
          </div>
        </div>

        <div class="author-section">
          <div class="participants">
          <span class="column">
            <i class="user-multiple column-icon"></i>
            회의 참여자
          </span>
            <button class="issue-button" @click="rightSideOn('participants')">참여자 추가하기</button>
            <div class="users-list">
              <div class="user-profile">
                <img :src=user2 alt="참여자">
                <span>강혜정</span>
              </div>
              <div class="user-profile">
                <img :src=user3 alt="참여자">
                <span>차윤슬</span>
              </div>
            </div>
          </div>
        </div>

        <div class="issue-section">
        <span class="column">
          <i class="label-add column-icon"></i>
          라벨 추가하기
        </span>
          <button class="issue-button" @click="rightSideOn('label')">라벨 추가하기</button>
          <button class="label-button">Frontend</button>
        </div>

        <!-- 태스크 추가하기 -->
        <div class="issue-section">
        <span class="column">
          <i class="task-add column-icon"></i>
          태스크 추가하기
        </span>
          <button class="issue-button" @click="rightSideOn('task')">태스크 연동하기</button>
          <span class="issue-id">User_001</span>
        </div>

        <!-- 이슈 추가하기 -->
        <div class="issue-section">
        <span class="column">
          <i class="issue-add column-icon"></i>
          이슈 추가하기
        </span>
          <button class="issue-button" @click="rightSideOn('issue')">이슈 연동하기</button>
          <span class="issue-id">User_001</span>
        </div>
      </div>

      <div class="btn-sector">
        <button class="save-button" @click="addNote">회의 저장하기</button>
        <button class="save-button" @click="addNote">회의록 추가하기</button>
      </div>
      <div v-show="isQuillVisible" class="quill-wrap">
        <QuillEditor ref="editor" class="content-editor" v-model="editor"/>
        <button class="save-button btn-v2" @click="saveNote">저장하기</button>
      </div>
    </div>
    <RightSideComponent v-show="rightSideVisible" :activeComponentId="activeComponentId"/>
  </div>
</template>

<style scoped>
.meeting-wrap {
  display: flex;
  gap: 1rem;
  height: 70vh;
}

.meeting-note-container {
  padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  //height: 100%;
  //overflow-y: auto;
  //display: flex;
  //flex-direction: column-reverse;
}

.meeting-input-wrap {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.meeting-title-container {
  display: flex;
}

.user-multiple {
  background-image: url("@/assets/icon/boardIcon/userMultiple.svg");
}

.column {
  display: flex;
  align-items: center;
  width: 10rem;
  gap: 10px;
}

.author-section {
  display: flex;
  flex-direction: column;
}

.issue-section {
  display: flex;
}

.author, .participants {
  display: flex;
  align-items: center;
}

.meeting-title {
  background-image: url("@/assets/icon/boardIcon/titleEdit.svg");
}

.meeting-description {
  background-image: url("@/assets/icon/boardIcon/quillDescription.svg");
}

.meeting-time {
  background-image: url("@/assets/icon/boardIcon/calendar.svg");
}

.meeting-time-input {
  display: flex;
  gap: 10px;
}

.column-icon {
  background-size: cover;
  width: 24px;
  height: 24px;
  display: block;
}

.user-editor {
  background-image: url("@/assets/icon/boardIcon/userEdit.svg");
}

.author img, .participants img {
  border-radius: 50%;
  width: 30px;
  height: 30px;
}

.label-add {
  background-image: url("@/assets/icon/boardIcon/labelIcon.svg");
}

.label-button {
  background-color: #FBDBEA;
  color: #DB2777;
  border: none;
  padding: 5px 10px;
  border-radius: 15px;
  cursor: pointer;
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

.issue-id {
  color: #28303F;
  background-color: #F3F6FF;
  padding: 5px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.issue-add {
  background-image: url("@/assets/icon/boardIcon/issueAdd.svg");
}

.task-add {
  background-image: url("@/assets/icon/boardIcon/taskAdd.svg");
}

.title-editor {
  font-size: 1.5rem;
  border: 0;
}

.time-editor{
  border: none;
}

.description-editor {
  font-size: 1rem;
  border: 0;
  font-weight: 400;
  width: 80%;
  //margin-left: 15px;
}


.content-editor {
  min-height: 200px;
  padding: 10px;
  background-color: white;
  margin-top: 10px;
  border-top: 1px solid #28303F;
  position: relative;
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
  //margin-left: auto;
}

.btn-ver2{
  color: #e0e8ff;
  background-color: #666daf;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
}

.users-list {
  display: flex;
  gap: 10px;
}

.btn-sector {
  display: flex;
  gap: 10px;
  width: 100%;
  justify-content: flex-end;
}

.fade-enter-active, .fade-leave-active {
  transition: transform 0.5s, opacity 0.5s;
}

.fade-enter, .fade-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

.fade-enter-to, .fade-leave {
  transform: translateX(0);
  opacity: 1;
}
</style>