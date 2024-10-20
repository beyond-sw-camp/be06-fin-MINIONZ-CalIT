<script setup>
import { useRoute } from 'vue-router';
import { inject, ref } from 'vue';
import { useMeetingStore } from "@/stores/scrum/useMeetingStore";
import RightSideComponent from "@/common/component/RightSide/RightSideComponent.vue";
import { timeInputUtils } from '@/utils/timeInputUtils';
import { setPersona } from "@/utils/personaUtils";
import router from "@/router";
import { useFriendsStore } from "@/stores/user/useFriendsStore";
import { useSprintLabelStore } from "@/stores/scrum/useSprintLabelStore";
import { useField, useForm } from 'vee-validate';
import * as yup from 'yup';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Meeting Create';
contentsDescription.value = '회의를 만들어 보세요!';

const friendsStore = useFriendsStore();
const sprintLabelStore = useSprintLabelStore();
const meetingStore = useMeetingStore();
const rightSideVisible = ref(false);
const activeComponentId = ref('');
const editor = ref(null);
const isQuillVisible = ref(false);
const participants = ref([]);

const { handleSubmit, errors } = useForm({
  validationSchema: yup.object({
    meetingTitle: yup.string().required('회의 제목을 입력하세요'),
    meetingDescription: yup.string().required('회의 설명을 입력하세요'),
    startTime: yup.date().required('시작 시간을 입력하세요'),
    endTime: yup.date().required('종료 시간을 입력하세요'),
  }),
});

const { value: meetingTitle } = useField('meetingTitle');
const { value: meetingDescription } = useField('meetingDescription');
const { value: startTime } = useField('startTime');
const { value: endTime } = useField('endTime');

const onSubmit = handleSubmit(async () => {
  const meeting = {
    meetingTitle: meetingTitle.value,
    meetingDescription: meetingDescription.value,
    startTime: timeInputUtils.validateTime(startTime.value),
    endTime: timeInputUtils.validateTime(endTime.value),
    participants: participants.value.map((participant) => participant.id),
    labels: sprintLabelStore.labels.map((label) => label.labelId),
  };
  await meetingStore.addMeeting(meeting, workspaceId);
  router.push(`/workspace/${workspaceId}/scrum/meeting/list`);
});

const addNote = () => {
  onSubmit();
  router.push(`/workspace/${workspaceId}/scrum/meeting/edit`);
};

const rightSideOn = (id) => {
  const meetingNoteContainer = document.querySelector('.meeting-note-container');
  if (meetingNoteContainer) {
    meetingNoteContainer.style.transition = 'width 0.5s ease';
    meetingNoteContainer.style.width = rightSideVisible.value ? '100%' : 'calc(100% - 300px)';
  }
  activeComponentId.value = id;
  rightSideVisible.value = !rightSideVisible.value;
};

const saveParticipantsToUserList = (newParticipants) => {
  participants.value = newParticipants;
};
</script>

<template>
  <div class="meeting-wrap">
    <div class="meeting-note-container">
      <div class="meeting-input-wrap">
        <div class="meeting-title-container">
          <span class="column">
            <i class="meeting-title column-icon"></i>
            회의 제목
          </span>
          <input v-model="meetingTitle" class="title-editor" placeholder="회의 제목을 입력하세요"/>
          <p class="error-message" v-if="errors.meetingTitle">{{ errors.meetingTitle }}</p>
        </div>
        <div class="issue-section">
          <span class="column">
            <i class="meeting-description column-icon"></i>
            설명 추가하기
          </span>
          <input v-model="meetingDescription" class="description-editor" placeholder="회의록의 설명을 입력하세요"/>
          <p class="error-message" v-if="errors.meetingDescription">{{ errors.meetingDescription }}</p>
        </div>

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
          <p class="error-message" v-if="errors.startTime">{{ errors.startTime }}</p>
          <p class="error-message" v-if="errors.endTime">{{ errors.endTime }}</p>
        </div>

        <div class="author-section">
          <div class="participants">
            <span class="column">
              <i class="user-multiple column-icon"></i>
              회의 참여자
            </span>
            <button class="issue-button" @click="rightSideOn('participants')">참여자 추가하기</button>
            <div class="users-list">
              <div class="user-profile" v-for="participant in participants"
                   :key="participant.searchUserIdx">
                <img :src="setPersona(participant.persona)" alt="참여자">
                <span>{{ participant.userName }}</span>
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
          <button v-for="(label, index) in sprintLabelStore.labels" :key="index" class="label-button" :style="`background-color:${label.color.backgroundColor}`">
            {{ label.labelName }}
          </button>
        </div>

        <div class="issue-section">
          <span class="column">
            <i class="issue-add column-icon"></i>
            이슈 추가하기
          </span>
          <button class="issue-button" @click="rightSideOn('issue')">이슈 추가하기</button>
          <span class="issue-id">User_001</span>
        </div>
      </div>

      <div class="btn-sector">
        <button class="save-button" @click="onSubmit">회의 저장하기</button>
        <button class="save-button btn-ver2" @click="addNote">회의록 추가하기</button>
      </div>
      <div v-show="isQuillVisible" class="quill-wrap">
        <QuillEditor ref="editor" class="content-editor" v-model="editor"/>
      </div>
    </div>
    <RightSideComponent v-show="rightSideVisible" :activeComponentId="activeComponentId" @update-meeting-participants="saveParticipantsToUserList" />
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

.description-editor {
  font-size: 1rem;
  border: 0;
  font-weight: 400;
  width: 80%;
}

.content-editor {
  min-height: 500px;
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
}

.btn-ver2 {
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

.time-editor {
  border: none;
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

.error-message {
  background-color: #fff1f1;
  padding: 0.625rem;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
  margin: 0;
}
</style>