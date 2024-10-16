<script setup>
import { ref, onMounted, defineEmits, defineProps } from 'vue';
import { useRoute } from 'vue-router';
import PerfectScrollbar from 'perfect-scrollbar';
import { useCalendar } from '@/utils/calendarUtils';
import { formatUtil } from '@/utils/scheduleDateFnsUtils';

const emit = defineEmits(['prevMonth', 'nextMonth']);

const props = defineProps({
  sprintData: {
    type: Array,
    required: true,
  },
  meetingData: {
    type: Array,
    required: true,
  },
});

onMounted(() => {
  const container = document.querySelector('.calendar-container');
  if (container) {
    new PerfectScrollbar(container);
  }

});

const route = useRoute();
const workspaceId = route.params.workspaceId;

const isVisible = ref(false);

const show = () => {
  isVisible.value = true;
};

const {
  currentYear,
  currentMonth,
  weekDays,
  daysInMonth,
  startBlankDays,
  goToToday,
} = useCalendar();


const meetingsForDay = (day) => {
  return props.meetingData?.filter(
      (meeting) => {
        try {
          return formatUtil(meeting.startDate, 'd') === String(day);
        } catch (error) {
          console.error('Failed to format meeting date:', error);
          return false;
        }
      }
  ) || [];
};

const sprintsForDay = (day) => {
  return props.sprintData?.filter(sprint => {
    const startDay = parseInt(formatUtil(sprint.startDate, 'd'), 10);
    return day === startDay;
  }).map(sprint => {
    const startDay = parseInt(formatUtil(sprint.startDate, 'd'), 10);
    const endDay = parseInt(formatUtil(sprint.endDate, 'd'), 10);
    const duration = endDay - startDay + 1;
    return {
      ...sprint,
      gridColumnStart: startDay,
      gridColumnEnd: `span ${duration}`,
    };
  }) || [];
};

const handlePrevMonth = () => {
  emit('prevMonth');
};

const handleNextMonth = () => {
  emit('nextMonth');
};
</script>

<template>
  <div class="calendar-container">
    <div class="calendar-header">
      <div class="today-tab">
        <button @click="goToToday">Today</button>
      </div>
      <div class="calendar-nav">
        <button @click="handlePrevMonth">&lt;</button>
        <span>{{ currentYear }}년 {{ currentMonth + 1 }}월</span>
        <button @click="handleNextMonth">&gt;</button>
      </div>
      <div class="calendar-tab">
        <router-link
            v-if="workspaceId"
            :to="`/workspace/${workspaceId}/schedule/monthly`"
            class="on"
        >Month
        </router-link>
        <router-link
            v-if="workspaceId"
            :to="`/workspace/${workspaceId}/schedule/weekly`"
            class="off"
        >Week
        </router-link>
        <router-link v-if="!workspaceId" :to="`/my/schedule/monthly`" class="on"
        >My Month
        </router-link>
        <router-link v-if="!workspaceId" :to="`/my/schedule/weekly`" class="off"
        >My Week
        </router-link>
      </div>
    </div>

    <div class="calendar-grid header">
      <div v-for="(day, index) in weekDays" :key="index" class="day-header">
        {{ day }}
      </div>
    </div>

    <div class="calendar-grid">

      <div v-for="n in startBlankDays" :key="n"></div>
      <div v-for="day in daysInMonth" :key="day" class="day-cell">
        <div class="day-number">{{ day }}</div>
        <div class="events">
          <div>
            <button
                v-for="event in sprintsForDay(day)"
                :key="event.id"
                class="event sprint"
                :style="{ gridColumn: `${event.gridColumnStart} / ${event.gridColumnEnd}` }"
                @click="show()"
            >
              {{ event.title }}
            </button>
          </div>
          <div>
            <button
                v-for="event in meetingsForDay(day)"
                :key="event.id"
                class="event meeting"
                @click="show()"
            >
              {{ event.title }}
            </button>
          </div>
        </div>
      </div>
    </div>
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
    font-size: 16px;
  }
}

.calendar-tab {
  display: flex;
  align-items: center;
  gap: 10px;
  background-color: #e0e8ff;
  padding: 5px 10px;
  border-radius: 25px;
  font-size: 15px;

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
  grid-auto-flow: dense;
}

.header .day-header {
  font-weight: 400;
  padding: 10px;
  background-color: rgba(224, 232, 255, 0.3);
  font-size: 16px;
  color: #28303f;
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
  min-height: 150px;
  position: relative;
  padding: 10px;
  overflow: visible;
}

.day-number {
  font-size: 16px;
  text-align: left;
}

.events {
  margin-top: 10px;
  position: relative;
}

.event {
  color: #28303f;
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
.event {

  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.event.meeting {
  border: 2px solid #2196f3;
  background-color: rgba(33, 150, 243, 0.1);
}

.event.sprint {
  border: 2px solid #db2777;
  background-color: rgba(219, 39, 119, 0.1);
  grid-column: span 2;
}
</style>