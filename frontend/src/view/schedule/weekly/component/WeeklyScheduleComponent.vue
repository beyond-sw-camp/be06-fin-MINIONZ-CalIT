<script setup>
import { ref, defineProps } from 'vue';
import meeting from '@/assets/icon/schedule/meeting.svg';

const props = defineProps(['meetings']);
const scheduleItems = ref(props.meetings.map(meeting => ({
  title: meeting.title,
  time: new Date(meeting.startDate).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
  icon: meeting
})));
</script>
rt
<template>
  <div class="weekly-schedule">
    <p class="weekly-schedule-title">Weekly Schedule</p>
    <div v-for="(item, index) in scheduleItems" :key="index" class="schedule-item">
      <img :src="meeting" alt="icon" class="schedule-icon" />
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
  //padding: 20px;
  width: 100%;
}

.weekly-schedule-title {
  font-weight: 500;
  font-size: 16px;
  margin-bottom: 10px;
}

.schedule-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  //background-color: rgba(243, 246, 255, 0.5);
  border-radius: 8px;
  background-color: #f9f9f9;
}

.schedule-icon {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.schedule-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  flex-direction: column;
}

.schedule-title {
  font-weight: 400;
  font-size: 14px;
}

.schedule-time {
  font-size: 12px;
  color: #777;
}
</style>