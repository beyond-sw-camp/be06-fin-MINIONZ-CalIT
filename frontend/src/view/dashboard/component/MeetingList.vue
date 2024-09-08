<script setup>
const meetings = [
  {
    id: 1,
    title: 'Analytic dashboard design',
    project: '워크스페이스 명',
    time: 'Today, 10:00am',
    date: 'Dec 15th, 2023',
  },
  {
    id: 2,
    title: 'Contact us section on landing page',
    project: 'Circles project',
    time: 'Today, 15:00pm',
    date: 'Dec 15th, 2023',
  },
];
</script>

<template>
  <div class="meeting-list">
    <h3>Upcoming Meetings</h3>
    <div class="meeting-card" v-for="meeting in meetings" :key="meeting.id">
      <h4>{{ meeting.title }}</h4>
      <p>{{ meeting.project }} - {{ meeting.time }}</p>
      <span>{{ meeting.date }}</span>
    </div>
  </div>
</template>

<style scoped>
.meeting-list {
  padding: 20px;
}

.meeting-card {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
}
</style>