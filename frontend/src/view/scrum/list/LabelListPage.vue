<script setup>
import {ref, computed, onMounted} from 'vue';
import LabelViewBar from '@/view/scrum/list/component/LabelViewBar.vue';
import {useSprintStore} from "@/stores/scrum/useSprintStore";
import {useTaskStore} from "@/stores/scrum/useTaskStore";
import {useMeetingStore} from "@/stores/scrum/useMeetingStore";

const currentView = ref('sprint');

const sprintStore = useSprintStore();
const taskStore = useTaskStore();
const meetingStore = useMeetingStore();

const items = computed(() => {
  if (currentView.value === 'sprint') {
    return sprintStore.sprints;
  } else if (currentView.value === 'task') {
    return taskStore.tasks;
  } else if (currentView.value === 'meeting') {
    return meetingStore.meetings;
  }
  return [];
});

onMounted(async () => {
  await sprintStore.getSprintList();
  await taskStore.getTaskList();
  await meetingStore.getMeetingList();
});
</script>

<template>
  <div class="board-list-container">
    <LabelViewBar :currentView="currentView" @view-change="currentView = $event"/>
    <div v-for="item in items" :key="item.id">
      {{ item.name }}
    </div>
  </div>
</template>

<style scoped>
.initial-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;

  a {
    padding: 10px 20px;
    background-color: #93AAFD;
    color: white;
    border-radius: 5px;
    text-decoration: none;

    &:hover {
      background-color: #6F8FFC;
    }
  }
}

.label-form-container {
  display: flex;
  flex-direction: column;
  background-color: #f7f8fa;
  padding: 20px;
  border-radius: 8px;
  border: 1px solid #D7DBEC;
  margin: 30px;
}

.label-preview {
  margin-bottom: 20px;
}

.label-box {
  padding: 5px 15px;
  background-color: #c5def5;
  border-radius: 15px;
  color: #000;
  font-weight: bold;
}

.label-inputs {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.input-group {
  display: flex;
  flex-direction: column;
  width: 30%;
}

.input-group label {
  font-weight: bold;
  margin-bottom: 5px;
}

input[type="text"] {
  padding: 10px;
  border: 1px solid #d1d5da;
  border-radius: 5px;
  font-size: 14px;
}

.color-group {
  display: flex;
  align-items: flex-start;
}

.color-selector {
  display: flex;
  align-items: center;
}

.color-randomizer {
  background-color: #f1f8ff;
  border: none;
  padding: 8px;
  border-radius: 5px;
  margin-right: 10px;
  cursor: pointer;
}

input[type="text"]#labelColor {
  width: 100px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.cancel-button {
  background-color: transparent;
  border: 1px solid #d1d5da;
  color: #333;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
}

.create-button {
  background-color: #28a745;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
}
</style>