<script setup>
import { inject, ref, computed } from 'vue';
import { useSprintStore } from "@/stores/workspace/scrum/useSprintStore";
import { useSprintLabelStore } from "@/stores/workspace/scrum/useSprintLabelStore";
import { timeInputUtils } from "@/utils/timeInputUtils";
import { useRoute } from "vue-router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = '스프린트 추가하기';
contentsDescription.value = '스프린트를 추가해보세요!';

const route = useRoute();
const workSpaceId = route.params.workspaceId;

const sprintStore = useSprintStore();
const sprintLabelStore = useSprintLabelStore();
const sprintTitle = ref('');
const sprintContent = ref('');
const participants = ref([]);
const selectedLabels = ref([]);
const startData = ref('');
const endData = ref('');
const labelSearch = ref('');

const filteredLabels = computed(() => {
  const allLabels = sprintLabelStore.getSprintLabel(workSpaceId);
  if (!labelSearch.value) {
    return allLabels;
  }
  return allLabels.filter(label => label.labelName.includes(labelSearch.value));
});

const selectLabel = (label) => {
  if (!selectedLabels.value.includes(label)) {
    selectedLabels.value.push(label);
  }
};

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
    const { start, end } = timeInputUtils(startData.value, endData.value);
    startData.value = start;
    endData.value = end;
  }
};
</script>

<template>
  <div class="form-container">
    <div class="workspace-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="sprintTitle">Sprint 이름</label>
            <input type="text" id="sprintTitle" v-model="sprintTitle" placeholder="Sprint 이름을 입력하세요" class="input-field">
          </div>
          <div>
            <label for="sprintContent">Sprint 내용</label>
            <input type="text" id="sprintContent" v-model="sprintContent" placeholder="Sprint 내용을 입력하세요" class="input-field">
          </div>
          <div>
            <label for="sprintParticipation">담당자 추가</label>
            <input type="text" id="sprintParticipation" placeholder="참여자를 검색해주세요" class="input-field">
            <ul>
              <li v-for="participant in participants" :key="participant">{{ participant }}</li>
            </ul>
          </div>
          <div>
            <label>시작 날짜</label>
            <input type="datetime-local" id="startDate" v-model="startData" class="input-field" @change="adjustTime">
          </div>
          <div>
            <label>종료 날짜</label>
            <input type="datetime-local" id="endDate" v-model="endData" class="input-field" @change="adjustTime">
          </div>
          <div>
            <input type="text" v-model="labelSearch" placeholder="label을 검색해주세요" class="input-field">
            <ul>
              <li v-for="label in filteredLabels" :key="label.labelId" @click="selectLabel(label)">
                {{ label.labelName }}
              </li>
            </ul>
            <ul>
              <li v-for="label in selectedLabels" :key="label.labelId">
                {{ label.labelName }}
              </li>
            </ul>
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