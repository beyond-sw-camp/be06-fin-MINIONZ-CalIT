<script setup>
import { ref, defineProps } from 'vue';
import issue from '@/assets/icon/schedule/meeting.svg';

const props = defineProps({ issues: { type: Array, default: () => [] } });
const scheduleItems = ref(
  Array.isArray(props.issues)
    ? props.issues.map((issue) => ({
        title: issue.title,
        time: new Date(issue.startDate).toLocaleTimeString([], {
          hour: '2-digit',
          minute: '2-digit',
        }),
        icon: issue,
      }))
    : []
);
</script>

<template>
  <div class="weekly-schedule">
    <p class="weekly-schedule-title">Weekly Schedule</p>
    <div v-if="scheduleItems.length === 0" class="no-issues">
      issue가 없습니다
    </div>
    <div v-else>
      <div v-for="(item, index) in issues" :key="index" class="schedule-item">
        <img :src="issue" alt="icon" class="schedule-icon" />
        <div class="schedule-info">
          <div class="schedule-title">{{ item.title }}</div>
          <div class="schedule-time">{{ item.time }}</div>
        </div>
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
