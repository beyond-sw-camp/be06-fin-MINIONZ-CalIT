<script setup>
import { ref, computed, onMounted, defineProps, defineEmits } from 'vue';
import { useRoute } from "vue-router";
import { formatUtil, getWeekRange, getWeekDaysUtil } from '@/utils/scheduleDateFnsUtils';
import PerfectScrollbar from 'perfect-scrollbar';
import ScheduleModal from "@/view/schedule/component/ScheduleModal.vue";

const props = defineProps({
  selectedWeek: {
    type: Array,
    required: true
  },
  sprintData: {
    type: Array,
    required: true,
  },
  meetingData: {
    type: Array,
    required: true,
  },
});

const emit = defineEmits(['update:selectedWeek']);

const route = useRoute();
const workspaceId = route.params.workspaceId;

onMounted(() => {
  const container = document.querySelector('.calendar-container');
  if (container) {
    new PerfectScrollbar(container);
  }
});

const today = ref(new Date());
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

const show = (clickEvent, event) => {
  modalProps.value = {
    title: event.title,
    startDate: event.startDate,
    endDate: event.endDate,
    contents: event.contents,
    participants: event.participants,
    top: clickEvent.clientY,
    left: clickEvent.clientX
  };
  isVisible.value = true;
  console.log(clickEvent.clientY, clickEvent.clientX);
  console.log(modalProps.value);
};

const goToToday = () => {
  const todayDate = new Date();
  emit('update:selectedWeek', [todayDate]);
};
const daysInWeek = computed(() =>
    getWeekDaysUtil(
        props.selectedWeek.length ? props.selectedWeek[0] : today.value
    ));
const prevWeek = () => {
  const prevDate = new Date(props.selectedWeek[0]);
  prevDate.setDate(prevDate.getDate() - 7);
  emit('update:selectedWeek', [prevDate]);
};

const nextWeek = () => {
  const nextDate = new Date(props.selectedWeek[0]);
  nextDate.setDate(nextDate.getDate() + 7);
  emit('update:selectedWeek', [nextDate]);
};

const events = ref([]);

const eventsForDay = (day) => {
  return events.value.filter(event => formatUtil(event.date, 'yyyy-MM-dd') === formatUtil(day, 'yyyy-MM-dd'));
};

function getEventTop(startDate) {
  const startHour = new Date(startDate).getHours();
  return (startHour * 80)+10;
}


function getEventWidth(startDate, endDate) {
  const start = new Date(startDate);
  const end = new Date(endDate);
  const duration = (end - start) / (1000 * 60 * 60 * 24);

  return Math.ceil(duration) + 1;
}

const sprintsForWeek = (weekStart) => {
  const weekEnd = new Date(weekStart);
  weekEnd.setDate(weekEnd.getDate() + 6);
  return props.sprintData?.filter(sprint => {
    const sprintStart = new Date(sprint.startDate);
    const sprintEnd = new Date(sprint.endDate);
    return (sprintStart >= weekStart && sprintStart <= weekEnd) || (sprintEnd >= weekStart && sprintEnd <= weekEnd) || (sprintStart <= weekStart && sprintEnd >= weekEnd);
  }).map(sprint => {
    const startDay = parseInt(formatUtil(sprint.startDate, 'd'), 10);
    const endDay = parseInt(formatUtil(sprint.endDate, 'd'), 10);
    const duration = endDay - startDay + 1;
    const sprintClass = {
      ...sprint,
      gridColumnStart: startDay,
      gridColumnEnd: `span ${duration}`,
    };
    if (new Date(sprint.startDate) < weekStart) {
      sprintClass.class = 'start';
    }
    if (new Date(sprint.endDate) > weekEnd) {
      sprintClass.class = 'end';
    }
    return sprintClass;
  }) || [];
};

</script>

<template>
  <div class="calendar-week-container">
    <div class="calendar-header">
      <div class="today-tab">
        <button @click="goToToday" class="today-btn">Today</button>
      </div>
      <div class="calendar-nav">
        <button @click="prevWeek" class="week-clicker">
          <i class="arrowL-icon"></i>
        </button>
        <span>{{ getWeekRange(props.selectedWeek.length ? props.selectedWeek[0] : today) }}</span>
        <button @click="nextWeek" class="week-clicker">
          <i class="arrowR-icon"></i>
        </button>
      </div>
      <div class="calendar-tab">
        <router-link :to="`/workspace/${workspaceId}/schedule/monthly`" class="off">Month</router-link>
        <router-link :to="`/workspace/${workspaceId}/schedule/weekly`" class="on">Week</router-link>
      </div>
    </div>

    <div class="calendar-grid">
      <div class="time-column">
        <div class="time-slot sprint-column">
          Sprint
        </div>
        <div class="time-slot" v-for="hour in hours" :key="hour">
          {{ hour }}
        </div>
      </div>

      <div class="days-column" v-for="day in daysInWeek" :key="day">
        <div class="day-header-wrap">
          <div class="day-header">{{ formatUtil(day, 'EEE, MMM d') }}</div>
        </div>

        <div class="events-column">
          <div class="sprint-period">
            <div class="sprints-column">
              <div v-for="sprint in sprintsForWeek(day)" :key="sprint.id" :class="['event', 'sprint', sprint.class]">
                {{ sprint.title }}
              </div>
            </div>
          </div>
          <div v-for="event in eventsForDay(day)" :key="event.id" class="event meetings"
               :style="{
                  top: getEventTop(event.startDate) + 'px',
                  gridColumn: 'span ' + getEventWidth(event.startDate, event.endDate),
                  backgroundColor: event.color
               }"
               @click="show(clickEvent, event)"
          >
            <div class="event-title">
              {{ event.title }}
            </div>
            <div class="event-details">
              {{ event.contents }}
            </div>
          </div>
        </div>
      </div>
    </div>
    <ScheduleModal
        v-if="isVisible"
        :title="modalProps.title"
        :contents="modalProps.contents"
        :start-date="modalProps.startDate"
        :end-date="modalProps.endDate"
        :participants="modalProps.participants"
        :top="30"
        :left="modalProps.left"
        @close="isVisible = false"/>
  </div>
</template>

<style scoped>
.calendar-week-container {
  height: calc(100% - 310px);
  position: relative;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  padding-bottom: 10px;
  button {
    background: #e0e8ff;
    color: #28303f;
    border: none;
    cursor: pointer;
    font-size: 14px;
  }
  .today-btn {
    border-radius: 20px;
    padding: 5px 10px;
  }
  .week-clicker {
    border-radius: 50%;
    display: flex;
    width: 36px;
    height: 36px;
    align-items: center;
    justify-content: center;
  }
}

.arrowL-icon {
  background-image: url('@/assets/icon/etc/arrowL.svg');
}

.arrowR-icon {
  background-image: url('@/assets/icon/etc/arrowR.svg');
}

.calendar-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
  i{
    width: 24px;
    height: 24px;
    background-size: cover;
    display: inline-block;
  }
  span {
    font-size: 18px;
    font-weight: 500;
  }
}

.calendar-tab {
  display: flex;
  align-items: center;
  gap: 10px;
  background-color: #e0e8ff;
  padding: 5px 10px;
  border-radius: 25px;
  font-size: 14px;
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
    padding-left: 10px;
    color: #28303f;
  }
}

.calendar-grid {
  display: grid;
  grid-template-columns: 50px repeat(7, 1fr);
  gap: 5px;
  overflow: scroll;
  height: 100%;
  box-sizing: border-box;
}

.time-column {
  display: flex;
  flex-direction: column;
  margin-top: 35px;
  border-radius: 15px;
  padding-top: 10px;
}

.time-slot {
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #555;
  &:nth-child(2) {
    border-radius: 15px 15px 0 0;
  }
  &:nth-child(n+2) {
    background-color: rgba(224, 232, 255, 0.5);
  }
}

.sprint-column {
  background-color: rgba(219, 39, 119, 0.1);
  border: 2px solid #db2777;
  border-radius: 30px;
  padding: 10px;
  height: 50px;
  margin-bottom: 10px;
}

.days-column {
  display: flex;
  flex-direction: column;
}

.day-header-wrap{
  background-color: #fff;
  position: sticky;
  top: 0;
  z-index: 10;
}

.day-header {
  font-size: 12px;
  font-weight: 500;
  height: 35px;
  display: flex;
  color: #28303F;
  background-color: #F6F8FF;
  border-radius: 15px 15px 0 0;
  box-shadow: 0 2px 2px rgba(0, 0, 0, 0.1);
  align-content: center;
  justify-content: center;
  flex-wrap: wrap;
}

.sprints-column{
  height: 70px;
  display: flex;
  flex-direction: column;
  gap: 5px;
  align-items: center;
  overflow: scroll;
  margin-top: 10px;
  border-bottom: 1px solid;

}

.events-column {
  background-color: rgba(249, 249, 249, 0.5);
  flex-grow: 1;
  border-radius: 8px;
  //padding: 10px;
  height: 80px;
  position: relative;
  grid-auto-rows: 80px;
  display: grid;
}

.event {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 8px;
  color: #28303F;
  font-size: 14px;
  cursor: pointer;
  font-weight: 500;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
  position: absolute;
  height: 80px;
  width: 90%;
  box-sizing: border-box;
  margin-left: 5%;
}

.event.meetings{
  border: 2px solid #2196f3;
  background-color: rgba(33, 150, 243, 0.1);
  margin: 0 10px;
  border-radius: 5px;
  width: calc(100% - 20px);
}

.event-title {
  display: flex;
  align-items: center;
}

.event-details {
  margin-top: 5px;
  font-size: 12px;
  color: #6b7280;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.event.sprint {
  //border: 2px solid #db2777;
  background-color: rgba(219, 39, 119, 0.1);
  grid-column: span 2;
  height: 30px;
  position: relative;
  margin: 0;
  line-height: 30px;
  padding: 0;
  text-align: center;
  width: 100%;
  border-radius: 0;
}

.event.sprint.start{
  border-left: 2px solid #db2777;
  margin-left: 10px;
  border-radius: 5px 0 0 5px;
  width: calc(100% - 10px);
}

.event.sprint.end{
  border-right: 2px solid #db2777;
  margin-right: 10px;
  border-radius: 0 5px 5px 0;
  width: calc(100% - 10px);
}
</style>