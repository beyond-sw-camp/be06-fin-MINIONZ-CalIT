<script setup>
import { ref, computed, onMounted, defineProps } from 'vue';
import { useRoute } from "vue-router";
import { formatUtil, getWeekDaysUtil } from '@/utils/dateUtils';
import { meetingData } from '@/static/meetingData';
import PerfectScrollbar from 'perfect-scrollbar';
import ScheduleModal from "@/view/schedule/component/ScheduleModal.vue";
import {getWeekRange} from "@/utils/timeUtils";

const props = defineProps({
  selectedWeek: {
    type: Array,
    required: true
  }
});

const route = useRoute();
const workspaceId = route.params.workspaceId;

onMounted(() => {
  const container = document.querySelector('.calendar-container');
  if (container) {
    new PerfectScrollbar(container);
  }
});
const daysInWeek = computed(() => getWeekDaysUtil(props.selectedWeek.length ? props.selectedWeek[0] : today));

const hours = ['7 AM', '8 AM', '9 AM', '10 AM', '11 AM', '12 PM', '1 PM', '2 PM', '3 PM', '4 PM', '5 PM', '6 PM', '7 PM', '8 PM', '9 PM'];

const isVisible = ref(false);
const modalProps = ref({
  title: '',
  startDate: '',
  endDate: '',
  contents: '',
  participants: [],
  top: 0,
  left: 0
});

const show = (event) => {
  modalProps.value = {
    title: event.title,
    startDate: event.startDate,
    endDate: event.endDate,
    contents: event.contents,
    participants: event.participants,
    top: event.clientY,
    left: event.clientX
  };
  isVisible.value = true;
  console.log(event.clientY, event.clientX);
  console.log(props.value);
};

const today = new Date();
const currentYear = ref(today.getFullYear());
const currentMonth = ref(today.getMonth());
// const weekDays = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];

// const daysInWeek = computed(() => getWeekDaysUtil(today));
// const startBlankDays = computed(() => {
//   return startOfMonthUtil(new Date(currentYear.value, currentMonth.value)).getDay();
// });

const goToToday = () => {
  currentYear.value = today.getFullYear();
  currentMonth.value = today.getMonth();
};

const prevWeek = () => {
  const prevDate = new Date(today);
  prevDate.setDate(today.getDate() - 7);
  today.setDate(prevDate.getDate());
};

const nextWeek = () => {
  const nextDate = new Date(today);
  nextDate.setDate(today.getDate() + 7);
  today.setDate(nextDate.getDate());
};

const events = ref(meetingData.map(meeting => ({
  id: meeting.id,
  date: new Date(meeting.startDate),
  title: meeting.title,
  startDate: meeting.startDate,
  endDate: meeting.endDate,
  contents: meeting.contents,
  participants: meeting.participants
})));

const eventsForDay = (day) => {
  return events.value.filter(event => formatUtil(event.date, 'yyyy-MM-dd') === formatUtil(day, 'yyyy-MM-dd'));
};
</script>


<template>
  <div class="calendar-week-container">
    <!-- 캘린더 네비게이션 (주간 이동) -->
    <div class="calendar-header">
      <div class="today-tab">
        <button @click="goToToday">Today</button>
      </div>
      <div class="calendar-nav">
        <button @click="prevWeek">◀</button>
        <span>{{ getWeekRange(props.selectedWeek.length ? props.selectedWeek[0] : today) }}</span>
        <button @click="nextWeek">▶</button>
      </div>
      <div class="calendar-tab">
        <router-link :to="`/workspace/${workspaceId}/schedule/monthly`" class="off">Month</router-link>
        <router-link :to="`/workspace/${workspaceId}/schedule/weekly`" class="on">Week</router-link>
      </div>
    </div>

    <!-- 시간대와 각 요일 표시 -->
    <div class="calendar-grid">
      <div class="time-column">
        <div class="time-slot" v-for="hour in hours" :key="hour">
          {{ hour }}
        </div>
      </div>

      <div class="days-column" v-for="day in daysInWeek" :key="day">
        <div class="day-header">{{ formatUtil(day, 'EEE, MMM d') }}</div>
        <div class="events-column">
          <div v-for="event in eventsForDay(day)" :key="event.id" class="event" :style="{ backgroundColor: event.color }" @click="show($event, event)">
            <div class="event-title">
              <img v-if="event.icon" :src="event.icon" alt="event icon" class="event-icon" />
              {{ event.title }}
            </div>
            <div class="event-details">
              <span v-if="event.isZoom" class="zoom-badge">Zoom</span>
              <span>{{ formatUtil(event.date, 'h:mm a') }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ScheduleModal
        v-if="isVisible"
        :title="events.title"
        :contents="events.contents"
        :start-date="events.startDate"
        :end-date="events.endDate"
        :participants="events.participants"
        :top="modalProps.top"
        :left="modalProps.left"
        @close="isVisible = false" />
  </div>
</template>

<style scoped>
/* 전체 컨테이너 */
.calendar-week-container {
  //max-width: 1200px;
  //margin: 0 auto;
  //padding: 20px;
  height: calc(100% - 350px);
  //overflow: scroll;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20px;
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
    padding-left: 10px;
  }
}

.calendar-grid {
  display: grid;
  grid-template-columns: 70px repeat(7, 1fr);
  gap: 10px;
  overflow: scroll;
  height: 100%;
  box-sizing: border-box;
}

.time-column {
  display: flex;
  flex-direction: column;
  margin-top: 35px;
}

.time-slot {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #555;
}

.days-column {
  display: flex;
  flex-direction: column;
}

.day-header {
  font-weight: 500;
  padding-bottom: 10px;
  text-align: center;
  color: #28303F;
  z-index: 10;
  background-color: rgba(224, 232, 255, 0.3);
  border-radius: 15px 15px 0 0;
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.1);
}

.events-column {
  background-color: #f9f9f9;
  flex-grow: 1;
  border-radius: 8px;
  padding: 10px;
  height: 80px;
}

.event {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 8px;
  color: #28303F;
  background-color: #e0e8ff;
  font-size: 14px;
  cursor: pointer;
  font-weight: 500;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
}

.event-title {
  display: flex;
  align-items: center;
}

.event-icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}

.zoom-badge {
  background-color: #007bff;
  color: white;
  padding: 3px 5px;
  border-radius: 5px;
  font-size: 12px;
  margin-right: 10px;
}

.event-details {
  margin-top: 5px;
  font-size: 12px;
  color: #28303F;
}
</style>