<script setup>
import { defineProps } from 'vue';
defineProps({
  task: {
    type: Object,
    required: true
  }
});
</script>

<template>
  <div class="task-card">
    <p>{{ task.title }}</p>
    <div class="labels">
      <span :key="taskNumber" class="label task-number">{{task.taskNumber}}</span>
      <span v-for="label in task.labels" :key="label" class="label">{{ label }}</span>
    </div>
    <div class="task-footer">
      <div class="avatars">
        <img v-for="(avatar, index) in task.avatars" :key="index" :src="avatar" alt="avatar" class="avatar"/>
        <span v-if="task.morePeople">+{{ task.morePeople }}</span>
      </div>
      <div>
        <span class="due-date" :key="task.dueDate">{{ task.dueDate }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.task-card {
  background-color: #F7F8FA;
  //border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
}

p{
  font-size: 12px;
  font-weight: 500;
}

.labels {
  margin: 10px 0;
}

.label {
  background-color: #e0f7fa;
  padding: 5px 10px;
  border-radius: 20px;
  margin-right: 5px;
  font-size: 12px;
  color: #28303F;
}
.task-number{
  background-color: #fff;
  border: 1px solid #ccc;
  color: #28303F;
}

.task-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.avatars {
  display: flex;
  align-items: center;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 5px;
}

.due-date{
  font-size: 10px;
}
</style>