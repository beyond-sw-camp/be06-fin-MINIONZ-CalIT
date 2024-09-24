<script setup>
import { ref, computed, onMounted } from 'vue';
import { getDaysInMonth, startOfMonth, format } from 'date-fns';
import { meetingData } from '@/static/meetingData';
import PerfectScrollbar from 'perfect-scrollbar';
import {useRoute} from "vue-router";
import { workspaceStore} from "@/stores/workspace/useWorkspaceStore";

onMounted(() => {
  const container = document.querySelector('.calendar-container');
  if (container) {
    new PerfectScrollbar(container);
  }
});

const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceStore.workspace.find(ws => ws.workspaceId === workspaceId));

const today = new Date();
const currentYear = ref(today.getFullYear());
const currentMonth = ref(today.getMonth());
const weekDays = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];

const daysInMonth = computed(() => getDaysInMonth(new Date(currentYear.value, currentMonth.value)));
const startBlankDays = computed(() => {
  return startOfMonth(new Date(currentYear.value, currentMonth.value)).getDay();
});

const goToToday = () => {
  currentYear.value = today.getFullYear();
  currentMonth.value = today.getMonth();
};

const nextMonth = () => {
  if (currentMonth.value === 11) {
    currentMonth.value = 0;
    currentYear.value++;
  } else {
    currentMonth.value++;
  }
};
const prevMonth = () => {
  if (currentMonth.value === 0) {
    currentMonth.value = 11;
    currentYear.value--;
  } else {
    currentMonth.value--;
  }
};

const events = ref(meetingData.map(meeting => ({
  id: meeting.id,
  date: new Date(meeting.startDate),
  title: meeting.title
})));

const eventsForDay = (day) => {
  return events.value.filter(event => format(event.date, 'd') === String(day));
};
</script>

<template>
  <div class="calendar-container">
    <!-- 상단 네비게이션: 이전/다음 월, 현재 월 표시 -->
    <div class="calendar-header">
      <div class="today-tab">
        <button @click="goToToday">Today</button>
      </div>
      <div class="calendar-nav">
        <button @click="prevMonth">&lt;</button>
        <span>{{ currentYear }}년 {{ currentMonth + 1 }}월</span>
        <button @click="nextMonth">&gt;</button>
      </div>
      <div class="calendar-tab">
        <router-link :to="`/workspace/${workspace}/schedule/monthly`" class="on">Month</router-link>
        <router-link :to="`/workspace/${workspace}/schedule/weekly`" class="off">Week</router-link>
      </div>
    </div>


    <!-- 달력 헤더: 요일 표시 -->
    <div class="calendar-grid header">
      <div v-for="(day, index) in weekDays" :key="index" class="day-header">{{ day }}</div>
    </div>

    <!-- 날짜와 이벤트 표시 -->
    <div class="calendar-grid">
      <!-- 빈 공간: 월 시작 전 빈칸 -->
      <div v-for="n in startBlankDays" :key="n"></div>

      <!-- 실제 날짜와 이벤트 -->
      <div v-for="day in daysInMonth" :key="day" class="day-cell">
        <div class="day-number">{{ day }}</div>
        <div class="events">
          <div
              v-for="event in eventsForDay(day)"
              :key="event.id"
              class="event"
          >
            {{ event.title }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.calendar-container {
  //max-width: 1000px;
  //margin: 0 auto;
  text-align: center;
  //padding: 30px;
}

.calendar-header{
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
  button{
    background: #e0e8ff;
    color: #666daf;
    border: none;
    cursor: pointer;
    padding: 5px 10px;
    border-radius: 15px;
    font-size: 14px;
  }
}

.calendar-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
  span{
    font-size: 18px;
  }
}

.calendar-tab {
  display: flex;
  align-items: center;
  gap: 10px;
  background-color: #e0e8ff;
  padding: 5px 10px;
  border-radius: 25px;
  a{
    color: #666daf;
    text-decoration: none;
  }
  .on{
    color: #666daf;
    background-color: white ;
    border-radius: 20px;
    font-weight: 500;
    padding: 5px 10px;
  }
  .off{
    padding-right: 10px;
  }
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  //gap: 10px;
}

.header .day-header {
  font-weight: 400;
  padding: 10px;
  background-color: #f7f8fa;
  font-size: 16px;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.15);
}

.day-cell {
  border: 1px solid #ddd;
  height: 100px;
  position: relative;
  padding: 10px;
}

.day-number {
  font-size: 16px;
  text-align: left;
}

.events {
  margin-top: 10px;
}

.event {
  background-color: #2196f3;
  color: white;
  border-radius: 5px;
  padding: 2px;
  font-size: 12px;
  margin-bottom: 2px;
}
</style>