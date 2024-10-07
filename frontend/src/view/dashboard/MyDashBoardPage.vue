<script setup>
import {inject, onMounted} from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import PriorityTask from "@/view/dashboard/component/PriorityTask.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import { useMyDashboardStore } from "@/stores/myspace/useMyDashboardStore";
import { useWorkspaceStore } from "@/stores/workspace/useWorkspaceStore";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Dashboard';
contentsDescription.value = '나의 할 일을 살펴보세요!';

const myDashboardStore = useMyDashboardStore();
const workspaceStore = useWorkspaceStore();

onMounted(async () => {
  await myDashboardStore.getMyDashboard();
});
</script>

<template>
  <div class="dashboard">
    <div v-if="workspaceStore.getAllWorkspace().length === 0">
      <TaskOverview
          v-if="myDashboardStore.mySprintData && myDashboardStore.mySprintData.progress && myDashboardStore.mySprintData.progress.successTaskCount
        !== undefined && myDashboardStore.mySprintData.progress.allTaskCount !== undefined"
          :completion-rate="myDashboardStore.mySprintData.progress.successTaskCount / myDashboardStore.mySprintData.progress.allTaskCount * 100"
          :tasks-completed="myDashboardStore.mySprintData.progress.successTaskCount"
          :total-tasks="myDashboardStore.mySprintData.progress.allTaskCount"
          :work-space-count="myDashboardStore.mySprintData.progress.workspaceCount"/>
      <PriorityTask :tasks="myDashboardStore.mySprintData.tasks"/>
      <MeetingList :meetings="myDashboardStore.mySprintData.meetings"/>
    </div>
    <div v-else class="initial-wrap">
      <p>워크스페이스와 스크럼을 추가하고 스크럼 관리를 시작해보세요!</p>
      <router-link to="/my/create">워크스페이스 추가하기</router-link>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.initial-wrap{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;
  a{
    padding: 10px 20px;
    background-color: #93AAFD;
    color: white;
    border-radius: 5px;
    text-decoration: none;
    &:hover{
      background-color: #6F8FFC;
    }
  }
}
</style>