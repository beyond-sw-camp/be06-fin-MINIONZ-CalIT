<script setup>
import { ref } from 'vue';
import { taskData  } from '@/static/taskData.js';

const tasks = ref(taskData.flatMap(group => group.tasks));

const timelineWeeks = ref(generateCurrentWeek());

function generateCurrentWeek() {
  const today = new Date();
  const week = [];
  for (let i = 0; i < 7; i++) {
    const day = new Date(today);
    day.setDate(today.getDate() + i);
    week.push(`${day.getMonth() + 1}/${day.getDate()}`);
  }
  return week;
}

function showPreviousWeek() {
  const today = new Date(timelineWeeks.value[0]);
  const previousWeek = [];
  for (let i = -7; i < 0; i++) {
    const day = new Date(today);
    day.setDate(today.getDate() + i);
    previousWeek.push(`${day.getMonth() + 1}/${day.getDate()}`);
  }
  timelineWeeks.value = previousWeek;
}

function showNextWeek(){
  const today = new Date(timelineWeeks.value[6]);
  const nextWeek = [];
  for (let i = 1; i < 8; i++) {
    const day = new Date(today);
    day.setDate(today.getDate() + i);
    nextWeek.push(`${day.getMonth() + 1}/${day.getDate()}`);
  }
  timelineWeeks.value = nextWeek;
}
</script>

<template>
  <div class="roadmap-container">
    <div class="timeline-header">
      <div class="header-left">
        <button @click="showPreviousWeek">이전 주</button>
        <button @click="showNextWeek">다음 주</button>
      </div>
      <div class="header-right">
        <div v-for="week in timelineWeeks" :key="week" class="timeline-week">{{ week }}</div>
      </div>

    </div>
    <div class="task-section">
<!--      <div v-for="task in tasks" :key="task.id" class="task-row">-->
<!--        <div class="task-name">-->
<!--          <i class="fas fa-plus"></i>-->
<!--          {{ task.name }}-->
<!--          <div class="avatars">-->
<!--            <img v-for="avatar in task.avatars" :key="avatar" :src="avatar" alt="avatar" class="avatar" />-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="task-timeline">-->
<!--          <div-->
<!--              v-for="milestone in task.milestones"-->
<!--              :key="milestone.id"-->
<!--              class="milestone"-->
<!--              :style="{-->
<!--              left: milestone.start + '%',-->
<!--              width: milestone.duration + '%',-->
<!--              backgroundColor: milestone.color-->
<!--            }"-->
<!--          >-->
<!--            <span class="milestone-label">{{ milestone.name }}</span>-->
<!--            <span class="milestone-percentage">{{ milestone.state }}%</span>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
      <div v-for="task in tasks" :key="task.id" class="task-row">
        <div class="task-name">
          <p>
            {{ task.title }}
          </p>
          <div class="avatars">
            <img v-for="avatar in task.avatars" :key="avatar" :src="avatar" alt="avatar" class="avatar" />
          </div>
        </div>
        <div class="task-timeline">
<!--          <div class="milestone">-->
<!--            <span class="milestone-percentage">{{ task.status }}</span>-->
<!--            <span class="milestone-dates">{{ task.startDate }} - {{ task.endDate }}</span>-->
<!--          </div>-->
          <div
              v-for="task in tasks"
              :key="task .id"
              class="milestone"
              :style="{
                left: task .start,
                width: task .duration + '%',
                backgroundColor: task.color
              }">
            <span class="milestone-label">{{ task.title }}</span>
            <span class="milestone-percentage">{{ task .status }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.roadmap-container {
  display: flex;
  flex-direction: column;
  padding: 30px;
}

.timeline-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  //margin-left: 20%;
}

.header-left{
  width: 20%;
}

.header-right{
  display: flex;
  //gap: 10px;
  width: 80%;
  justify-content: space-between;
}

.timeline-week {
  width: 6%;
  text-align: center;
  font-weight: 500;
}

.task-section {
  display: flex;
  flex-direction: column;
}

.task-row {
  display: flex;
  margin-bottom: 10px;
}

.task-name {
  display: flex;
  align-items: center;
  width: 20%;
  font-weight: 500;
  p{
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 70%;
  }
}

.avatars {
  margin-left: 10px;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-left: -10px;
  border: 2px solid white;
}

.task-timeline {
  position: relative;
  width: 80%;
  background-color: #f1f1f1;
  height: 50px;
  border-radius: 10px;
}

.milestone {
  position: absolute;
  height: 100%;
  border-radius: 25px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  color: #DB2777;
  background-color: #FBDBEA;
}

.milestone-label {
  font-size: 14px;
  font-weight: 500;
}

.milestone-percentage {
  font-size: 12px;
  background-color: rgba(255, 255, 255, 0.2);
  padding: 2px 5px;
  border-radius: 5px;
}

.milestone-dates {
  font-size: 12px;
  margin-left: 10px;
  color: #666;
}
</style>