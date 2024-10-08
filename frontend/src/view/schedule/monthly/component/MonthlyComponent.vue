<script setup>
import {ref, onMounted} from 'vue';
import {useRoute} from "vue-router";
import PerfectScrollbar from 'perfect-scrollbar';
import ScheduleModal from "@/view/schedule/component/ScheduleModal.vue";
import {useCalendar} from '@/utils/calendarUtils';
import {formatUtil} from "@/utils/dateUtils";

onMounted(() => {
  const container = document.querySelector('.calendar-container');
  if (container) {
    new PerfectScrollbar(container);
  }
  if (props.value.meetings) setEvents(props.value.meetings);
});

const route = useRoute();
const workspaceId = route.params.workspaceId;

const isVisible = ref(false);
const props = ref({
  title: '',
  startDate: '',
  endDate: '',
  contents: '',
  participants: [],
  top: 0,
  left: 0,
  meeting: []
});

const show = (event, eventData) => {
  props.value = {
    title: eventData.title,
    startDate: eventData.startDate,
    endDate: eventData.endDate,
    contents: eventData.contents,
    participants: eventData.participants,
    top: event.clientY,
    left: event.clientX
  };
  isVisible.value = true;
  console.log(event.clientY, event.clientX);
  console.log(props.value);
};

const {
  currentYear,
  currentMonth,
  weekDays,
  daysInMonth,
  startBlankDays,
  goToToday,
  prevMonth,
  nextMonth
} = useCalendar();

const events = ref([]);

const setEvents = (meetings) => {
  events.value = meetings.map(meeting => ({
    id: meeting.id,
    date: new Date(meeting.startDate),
    title: meeting.title,
    startDate: meeting.startDate,
    endDate: meeting.endDate,
    contents: meeting.contents,
    participants: meeting.participants
  }));
};
const eventsForDay = (day) => {
  return events.value.filter(event => formatUtil(event.date, 'd') === String(day));
};
</script>

<template>
  <div class="calendar-container">
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
        <router-link
            v-if="workspaceId"
            :to="`/workspace/${workspaceId}/schedule/monthly`"
            class="on">Month
        </router-link>
        <router-link
            v-if="workspaceId"
            :to="`/workspace/${workspaceId}/schedule/weekly`"
            class="off">Week
        </router-link>
        <router-link
            v-if="!workspaceId"
            :to="`/my/schedule/monthly`"
            class="on">My Month
        </router-link>
        <router-link
            v-if="!workspaceId"
            :to="`/my/schedule/weekly`"
            class="off">My Week
        </router-link>
      </div>
    </div>

    <div class="calendar-grid header">
      <div v-for="(day, index) in weekDays" :key="index" class="day-header">{{ day }}</div>
    </div>

    <div class="calendar-grid">
      <div v-for="n in startBlankDays" :key="n"></div>
      <div v-for="day in daysInMonth" :key="day" class="day-cell">
        <div class="day-number">{{ day }}</div>
        <div class="events">
          <button
              v-for="event in eventsForDay(day)"
              :key="event.id"
              class="event"
              @click="show($event, event)"
          >
            {{ event.title }}
          </button>
        </div>
      </div>
    </div>
    <ScheduleModal
        v-if="isVisible"
        :title="props.value.title"
        :contents="props.value.contents"
        :start-date="props.value.startDate"
        :end-date="props.value.endDate"
        :participants="props.value.participants"
        :top="props.value.top"
        :left="props.value.left"
        @close="isVisible = false"/>
  </div>
</template>

<style scoped>
.calendar-container {
  text-align: center;
  position: relative;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;

  button {
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

  span {
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

  a {
    color: #666daf;
    text-decoration: none;
  }

  .on {
    color: #666daf;
    background-color: white;
    border-radius: 20px;
    font-weight: 500;
    padding: 5px 10px;
  }

  .off {
    padding-right: 10px;
  }
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
}

.header .day-header {
  font-weight: 400;
  padding: 10px;
  background-color: rgba(224, 232, 255, 0.3);
  font-size: 16px;
  color: #28303F;
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.15);
}

.header .day-header:first-child {
  border-radius: 15px 0 0 0;
}

.header .day-header:last-child {
  border-radius: 0 15px 0 0;
}

.day-cell {
  border: 1px solid #ddd;
  height: 150px;
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
  border: 2px solid #2196f3;
  background-color: rgba(33, 150, 243, 0.1);
  color: #28303F;
  border-radius: 5px;
  padding: 5px;
  font-size: 13px;
  margin-bottom: 2px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 100%;
  text-align: left;
}
</style>