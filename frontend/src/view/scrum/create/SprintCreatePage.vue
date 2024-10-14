<script setup>
import { inject, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { VTextField, VSelect } from 'vuetify/components';
// import { useUserStore } from "@/stores/user/useUserStore";
import { useSprintStore } from '@/stores/scrum/useSprintStore';
import { useSprintLabelStore } from '@/stores/scrum/useSprintLabelStore';
import { useFriendsStore } from "@/stores/user/useFriendsStore";
import router from "@/router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = '스프린트 추가하기';
contentsDescription.value = '스프린트를 추가해보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const sprintStore = useSprintStore();
const friend = useFriendsStore();
// const userStore = useUserStore();

const sprintTitle = ref('');
const sprintContent = ref('');
const participants = ref([]);
const startDate = ref('');
const endDate = ref('');
const availableLabels = ref([]);
const availableParticipants = ref([]);

const addSprint = () => {
  sprintStore.addSprint({
    workspaceId: workspaceId,
    sprintTitle: sprintTitle.value,
    sprintContents: sprintContent.value,
    labels:[],
    participants: participants.value.map(participant => participant.searchUserIdx),
    startDate: startDate.value,
    endDate: endDate.value,
  });
  router.push(`/workspace/${workspaceId}/scrum/sprint/list`)
};

const fetchParticipants = async () => {
  await friend.getFriendsList(workspaceId);
  if (friend.friends) {
    availableParticipants.value = friend.friends.map(friend => friend.userName);
  } else {
    availableParticipants.value = [];
  }
};

const fetchLabels = async () => {
  const labelStore = useSprintLabelStore();
  await labelStore.getSprintLabel(workspaceId);
  availableLabels.value = labelStore.labels;
};

onMounted(() => {
  fetchParticipants();
  fetchLabels();
});
</script>

<template>
  <div class="form-container">
    <div class="workspace-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="sprintTitle">Sprint 이름</label>
            <VTextField
                v-model="sprintTitle"
                label="Sprint 이름"
                placeholder="Sprint 이름을 입력하세요"
            />
          </div>
          <div>
            <label for="sprintContent">Sprint 내용</label>
            <VTextField
                v-model="sprintContent"
                label="Sprint 내용"
                placeholder="Sprint 내용을 입력하세요"
            />
          </div>
          <div>
            <label for="participants">참여자 선택</label>
            <VSelect
                v-model="participants"
                :items="availableParticipants"
                label="참여자 선택"
                multiple
            />
          </div>
          <div>
            <label>시작 날짜</label>
            <VTextField
                v-model="startDate"
                label="시작 날짜"
                type="datetime-local"
            />
          </div>
          <div>
            <label>종료 날짜</label>
            <VTextField
                v-model="endDate"
                label="종료 날짜"
                type="datetime-local"
            />
          </div>
          <div>
            <label>라벨 선택</label>
            <VSelect
                v-model="selectedLabels"
                :items="availableLabels"
                label="라벨 선택"
                multiple
                />
          </div>
        </div>
      </div>
      <div class="button-wrap">
        <button @click="addSprint" class="add-workspace-btn">Sprint 추가</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

h2 {
  font-size: 24px;
  font-weight: 500;
  margin: 0;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

label {
  display: block;
  font-weight: 400;
  margin-top: 15px;
  font-size: 16px;
  margin-bottom: 5px;
}

.workspace-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.input-field {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.add-workspace-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 20px;
}

.add-workspace-btn:hover {
  background-color: #93AAFD;
}
</style>