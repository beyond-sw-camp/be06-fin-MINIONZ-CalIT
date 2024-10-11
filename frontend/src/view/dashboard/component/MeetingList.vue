<script setup>
import { computed, defineProps } from 'vue';
import { getBackgroundColor } from '@/utils/meetingBgUtils';

const props = defineProps({
  meetings: {
    type: Array,
    required: true,
  },
});

const meetingBackgroundColors = computed(() => {
  return props.meetings.map((meeting) => getBackgroundColor(meeting.date));
});
</script>

<template>
  <div class="meeting-list">
    <h3>Upcoming Meetings</h3>
    <div class="meeting-card-area" v-if="props.meetings.length > 0">
      <div
        class="meeting-card"
        v-for="(meeting, index) in meetings"
        :key="meeting.id"
      >
        <div class="head">
          <h4>{{ meeting.title }}</h4>
          <p>{{ meeting.project }}</p>
        </div>
        <div
          class="time"
          :style="{ backgroundColor: meetingBackgroundColors[index] }"
        >
          <span>{{ meeting.date }}</span>
        </div>
      </div>
    </div>
    <div v-else>다가오는 회의가 없습니다!</div>
  </div>
</template>

<style scoped>
.meeting-list {
  h3 {
    font-size: 20px;
    font-weight: 500;
    margin: 0 0 1rem;
  }
}

.meeting-card-area {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.meeting-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  width: 236px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 20px;
}

h4 {
  font-size: 1rem;
  font-weight: 500;
}

p {
  font-size: 12px;
  color: #909090;
}

.time {
  font-size: 12px;
  padding: 4px 12px;
  border-radius: 4px;
  width: fit-content;
}
</style>
