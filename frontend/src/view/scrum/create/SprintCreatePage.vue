<script setup>
import { inject, ref, onMounted, defineExpose, watch } from 'vue';
import { useRoute } from 'vue-router';
import { VTextField } from 'vuetify/components';
import { useSprintStore } from '@/stores/scrum/useSprintStore';
import { useWorkspaceStore } from '@/stores/workspace/useWorkspaceStore';
import { timeInputUtils } from '@/utils/timeInputUtils';
// import SearchLabels from '@/common/component/search/SearchLabels.vue';
import SearchFriends from '@/common/component/search/SearchFriends.vue';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = '스프린트 추가하기';
contentsDescription.value = '스프린트를 추가해보세요!';

const route = useRoute();
const workSpaceId = route.params.workspaceId;

const sprintStore = useSprintStore();
const workspaceStore = useWorkspaceStore();

const sprintTitle = ref('');
const sprintContent = ref('');
const participants = ref([]);
const selectedLabels = ref([]);
const startData = ref('');
const endData = ref('');
const availableParticipants = ref([]);

const addSprint = () => {
  sprintStore.addSprint({
    workspaceId: workSpaceId,
    sprintTitle: sprintTitle.value,
    sprintContents: sprintContent.value,
    labels: selectedLabels.value,
    participants: participants.value,
    startDate: startData.value,
    endDate: endData.value,
  });
};

const adjustTime = () => {
  if (startData.value && endData.value) {
    const { start, end } = timeInputUtils.adjustTime(startData.value, endData.value);
    startData.value = start;
    endData.value = end;
  }
};

const fetchParticipants = async () => {
  await workspaceStore.getAllWorkspace();
  const workspace = workspaceStore.workspace.value?.find(ws => ws.workspaceId === workSpaceId);
  if (workspace) {
    availableParticipants.value = workspace.participants;
  }
};

onMounted(fetchParticipants);

defineExpose({
  sprintTitle,
  sprintContent,
  participants,
  selectedLabels,
  startData,
  endData,
  addSprint,
  adjustTime,
  availableParticipants
});

watch([startData, endData], adjustTime);
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
            <label for="sprintParticipation">참여자 추가</label>
            <SearchFriends
                v-model="participants"
                :availableParticipants="availableParticipants"
            />
          </div>
          <div>
            <label>시작 날짜</label>
            <VTextField
                v-model="startData"
                label="시작 날짜"
                type="datetime-local"
                @change="adjustTime"
            />
          </div>
          <div>
            <label>종료 날짜</label>
            <VTextField
                v-model="endData"
                label="종료 날짜"
                type="datetime-local"
                @change="adjustTime"
            />
          </div>
<!--          <div>-->
<!--            <label>라벨 추가하기</label>-->
<!--            <SearchLabels-->
<!--                v-model="selectedLabels"-->
<!--                :workspace-id="workSpaceId"-->
<!--            />-->
<!--          </div>-->
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