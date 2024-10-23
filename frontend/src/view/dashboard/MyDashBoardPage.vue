<script setup>
import { inject, onMounted, ref } from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import PriorityTask from "@/view/dashboard/component/PriorityTask.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import { useMyDashboardStore } from "@/stores/myspace/useMyDashboardStore";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Dashboard';
contentsDescription.value = '나의 할 일을 살펴보세요!';

const myDashboardStore = useMyDashboardStore();
const mySprintData = ref(null);

onMounted(async () => {
  await myDashboardStore.getMyDashboard();
  mySprintData.value = myDashboardStore.mySprintData;
});
</script>

<template>
    <div v-if="mySprintData && mySprintData.progress" class="dashboard">
        <TaskOverview
          :completion-rate="mySprintData.progress.successTaskCount === 0 ? 0 : (mySprintData.progress.successTaskCount / mySprintData.progress.allTaskCount) * 100"
          :tasks-completed="mySprintData.progress.successTaskCount" :total-tasks="mySprintData.progress.allTaskCount"
          :work-space-count="mySprintData.progress.workspaceCount" />
        <PriorityTask :tasks="mySprintData.priorityTasks" />
        <MeetingList :meetings="mySprintData.upcomingMeetings" />
      </div>
    <div v-else>
      <div class="initial-wrap">
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

.initial-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;
}

.initial-wrap a {
  padding: 10px 20px;
  background-color: #93AAFD;
  color: white;
  border-radius: 5px;
  text-decoration: none;
}

.initial-wrap a:hover {
  background-color: #6F8FFC;
}
</style>