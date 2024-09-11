<script setup>
import { inject } from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import BurndownChart from "@/view/dashboard/component/BurndownChart.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'MINION Dashboard';
contentsDescription.value = '워크스페이스의 할일을 살펴보세요!';
</script>

<template>
  <div class="dashboard">
    <TaskOverview />
    <BurndownChart />
    <MeetingList />
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>