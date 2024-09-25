<script setup>
import { inject } from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import PriorityTask from "@/view/dashboard/component/PriorityTask.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Dashboard';
contentsDescription.value = '나의 할일을 살펴보세요!';
</script>

<template>
  <div class="dashboard">
    <TaskOverview />
    <PriorityTask />
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