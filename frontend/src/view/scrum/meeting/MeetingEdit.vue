<script setup>
import { useRoute } from 'vue-router';
import { computed, inject, onMounted, ref } from 'vue';
import { workspaceData } from '@/static/workspaceData';
import Quill from 'quill';
import 'quill/dist/quill.bubble.css';

import user1 from '@/assets/icon/persona/user1.svg';
import user2 from '@/assets/icon/persona/user2.svg';
import user3 from '@/assets/icon/persona/user3.svg';

const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = workspace.value ? `${workspace.value.workspaceName} Meeting` : 'Meeting Editor';
contentsDescription.value = '회의록을 작성해 보세요!';

const editor = ref(null);
const meetingTitle = ref('회의록 제목');

onMounted(() => {
  if (editor.value) {
    new Quill(editor.value, {
      theme: 'bubble',
      placeholder: '내용을 입력하세요...',
      modules: {
        toolbar: {
          container: [
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline'],
            ['image', 'code-block'],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'script': 'sub'}, { 'script': 'super' }],
            [{ 'indent': '-1'}, { 'indent': '+1' }],
            [{ 'direction': 'rtl' }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'align': [] }],
            ['clean']
          ],
          handlers: {
            'image': function() {
              const range = this.quill.getSelection();
              const value = prompt('Enter image URL');
              if (value) {
                this.quill.insertEmbed(range.index, 'image', value, Quill.sources.USER);
              }
            }
          }
        }
      }
    });
  }
});

const saveNote = () => {
  const noteContent = editor.value.querySelector('.ql-editor').innerHTML;
  console.log('Saved note:', noteContent);
  console.log('Meeting title:', meetingTitle.value);
  // TODO 백엔드로 전송하거나 로컬 저장소에 저장하는 로직을 추가
};

document.addEventListener('click', function(event) {
  if (event && event.target) {
    console.log('Clicked element:', event.target);
  } else {
    console.error('Event or event.target is undefined');
  }
});
</script>

<template>
  <div class="meeting-note-container">
    <div class="meeting-title-container">

      <span class="column">
        <i class="meeting-title column-icon"></i>
        회의록 제목
      </span>
      <input v-model="meetingTitle" class="title-editor" placeholder="회의록 제목" />
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
      <div class="participants">
        <span class="column">
          <i class="user-multiple column-icon"></i>
          회의 참여자
        </span>
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

    <!-- 이슈 추가하기 -->
    <div class="issue-section">
      <span class="column">
        <i class="issue-add column-icon"></i>
        이슈 추가하기
      </span>
      <button class="issue-button">이슈 연동하기</button>
    </div>

    <!-- Quill 에디터 섹션 -->
    <div class="editor-section">
      <span class="column">
        <i class="quill-editings column-icon"></i>
        회의록 작성하기
      </span>
      <div ref="editor" class="content-editor"></div>
    </div>

    <!-- 저장 버튼 -->
    <button class="save-button" @click="saveNote">저장하기</button>
  </div>
</template>

<style scoped>
.meeting-note-container {
  padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.meeting-title-container{
  display: flex;
}

.user-multiple {
  background-image: url("@/assets/icon/boardIcon/userMultiple.svg");
}

.column{
  display: flex;
  align-items: center;
  width: 10rem;
  gap: 10px;
}

.author-section {
  //margin-bottom: 20px;
  display: flex;
  flex-direction: column;
}

.issue-section{
  display: flex;
}

.author, .participants {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.meeting-title{
  background-image: url("@/assets/icon/boardIcon/titleEdit.svg");
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

.issue-button {
  background-color: #f8d7da;
  color: #c82333;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.issue-add{
  background-image: url("@/assets/icon/boardIcon/issueAdd.svg");
}

.editor-section {
  margin-top: 30px;
}

.quill-editings {
  background-image: url("@/assets/icon/boardIcon/quillEditor.svg");
}

.title-editor {
  //width: 100%;
  //padding: 10px;
  font-size: 1.5rem;
  border: 0;
  //border-radius: 5px;
  //margin-bottom: 20px;
}

.content-editor {
  min-height: 200px;
  //border: 1px solid #ddd;
  //border-radius: 5px;
  padding: 10px;
  background-color: white;
  margin-top: 10px;
  border-top: 1px solid #28303F;
  position: relative;
}

.save-button {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  width: 150px;
  margin-left: auto;
}

.user-profile{
  display: flex;
  align-items: center;
  gap: 10px;
}

.users-list{
  display: flex;
  gap: 10px;
}

.ql-editor {
  min-height: 200px;
  font-size: 1rem;
  line-height: 1.5;
  color: #28303F;
}

.ql-tooltip {
  position: fixed !important;
  z-index: 10000 !important;
  top: 100px !important;
  left: 50% !important;
}

.ql-container .ql-tooltip {
  z-index: 10000 !important;
}

.ql-container {
  position: relative;
  overflow: visible;
  z-index: auto;
}
</style>