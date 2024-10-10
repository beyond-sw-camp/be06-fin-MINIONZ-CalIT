<script setup>
import { ref, defineProps } from 'vue';
import taskImg from '@/assets/icon/schedule/task.svg';

const props = defineProps(['tasks']);
const taskWeek = ref((props.tasks || []).map(task => ({
  title: task.title,
  details: task.label,
  icon: task
})));
</script>

<template>
  <div class="imminent-tasks">
    <p class="imminent-tasks-title">Imminent tasks</p>
    <div v-for="(task, index) in taskWeek" :key="index" class="task-item">
      <img :src="taskImg" alt="icon" class="task-icon" />
      <div class="task-info">
        <div class="task-title">{{ task.title }}</div>
        <div class="task-details">{{ task.details }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.imminent-tasks {
  background-color: #fff;
  border-radius: 10px;
  //padding: 20px;
  width: 100%;
}

.imminent-tasks-title {
  font-weight: 500;
  font-size: 16px;
  margin-bottom: 10px;
}

.task-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  //background-color: rgba(243, 246, 255, 0.7);
  background-color: #f9f9f9;
  border-radius: 8px;
}

.task-icon {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.task-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  flex-direction: column;
}

.task-title {
  font-weight: 400;
  font-size: 14px;
}

.task-details {
  font-size: 12px;
  color: #777;
}
</style>