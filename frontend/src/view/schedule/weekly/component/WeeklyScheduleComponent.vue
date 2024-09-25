<script setup>
import { ref } from 'vue';

const scheduleItems = ref([
  { title: '회의1', time: '10:00 am', icon: 'https://via.placeholder.com/30' },
  { title: '회의2', time: '10:00 am', icon: 'https://via.placeholder.com/30' },
  { title: '회의3', time: '10:00 am', icon: 'https://via.placeholder.com/30' }
]);
</script>

<template>
  <div class="weekly-schedule">
    <h3>Weekly Schedule</h3>
    <div v-for="(item, index) in scheduleItems" :key="index" class="schedule-item">
      <img :src="item.icon" alt="icon" class="schedule-icon" />
      <div class="schedule-info">
        <div class="schedule-title">{{ item.title }}</div>
        <div class="schedule-time">{{ item.time }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.weekly-schedule {
  background-color: #fff;
  border-radius: 10px;
  padding: 20px;
  width: 100%;
}

.schedule-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.schedule-icon {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.schedule-info {
  display: flex;
  flex-direction: column;
}

.schedule-title {
  font-weight: bold;
  font-size: 14px;
}

.schedule-time {
  font-size: 12px;
  color: #777;
}
</style>