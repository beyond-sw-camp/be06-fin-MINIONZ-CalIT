<script setup>
import { ref, computed, defineEmits } from 'vue';
import { startOfMonth, format, getDay, getDaysInMonth, addMonths, subMonths, addDays, startOfWeek, endOfWeek, isSameDay } from 'date-fns';

const emit = defineEmits(['update:selectedWeek']);

const today = ref(new Date());
const currentYear = ref(today.value.getFullYear());
const currentMonth = ref(today.value.getMonth());
const weekDays = ['Sun','Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
const daysInMonth = computed(() => getDaysInMonth(new Date(currentYear.value, currentMonth.value)));
const startBlankDays = computed(() => Math.max(0, getDay(startOfMonth(new Date(currentYear.value, currentMonth.value))) - 1));
const selectedWeek = ref([]);
const currentMonthLabel = computed(() => format(new Date(currentYear.value, currentMonth.value), 'MMMM yyyy'));
const nextMonth = () => {
  currentMonth.value = addMonths(new Date(currentYear.value, currentMonth.value), 1).getMonth();
};
const prevMonth = () => {
  currentMonth.value = subMonths(new Date(currentYear.value, currentMonth.value), 1).getMonth();
};

const emitSelectedWeek = (week) => {
  emit('update:selectedWeek', week);
};
const selectDateRange = (day) => {
  const selectedDate = new Date(currentYear.value, currentMonth.value, day);
  selectedWeek.value = getWeekRange(selectedDate);
  emitSelectedWeek(selectedWeek.value);
};
const getWeekRange = (date) => {
  const start = startOfWeek(date, { weekStartsOn: 0 });
  const end = endOfWeek(date, { weekStartsOn: 0 });
  const days = [];
  let day = start;
  while (day <= end) {
    days.push(day);
    day = addDays(day, 1);
  }
  return days;
};

const isSelected = (day) => {
  const selectedDate = new Date(currentYear.value, currentMonth.value, day);
  return selectedWeek.value.some(selected => isSameDay(selected, selectedDate));
};

const isInRange = (day) => {
  const selectedDate = new Date(currentYear.value, currentMonth.value, day);
  return selectedWeek.value.some(selected => isSameDay(selected, selectedDate));
};

const isToday = (day) => {
  const todayDate = new Date();
  const selectedDate = new Date(currentYear.value, currentMonth.value, day);
  return isSameDay(todayDate, selectedDate);
};
</script>

<template>
  <div class="calendar-container">
    <div class="calendar-header">
      <span>{{ currentMonthLabel }}</span>
      <div class="btn-bundle">
        <button @click="prevMonth">
          <i class="arrowL-icon"></i>
        </button>
        <button @click="nextMonth">
          <i class="arrowR-icon"></i>
        </button>
      </div>
    </div>

    <div class="calendar-grid header">
      <div v-for="day in weekDays" :key="day" class="day-header">{{ day }}</div>
    </div>

    <div class="calendar-grid">
      <div v-for="n in startBlankDays" :key="n"></div>
      <div
          v-for="day in daysInMonth"
          :key="day"
          class="day-cell"
          :class="{ selected: isSelected(day), inRange: isInRange(day), today: isToday(day) }"
          @click="selectDateRange(day)"
      >
        {{ day }}
      </div>
    </div>
  </div>
</template>

<style scoped>
.calendar-container {
  width: 320px;
  margin-left: 20px;
  text-align: center;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  span{
    font-weight: 500;
  }
  button {
    background: #F3F6FF;
    color: #666daf;
    border: none;
    cursor: pointer;
    border-radius: 50%;
    font-size: 14px;
    display: flex;
    width: 24px;
    height: 24px;
    align-items: center;
    justify-content: center;
    i{
      width: 12px;
      height: 12px;
      background-size: cover;
      display: inline-block;
    }
  }
}

.btn-bundle{
  display: flex;
  gap: 10px;
  align-items: center;
}

.arrowL-icon {
  background-image: url('@/assets/icon/etc/arrowL.svg');
}

.arrowR-icon {
  background-image: url('@/assets/icon/etc/arrowR.svg');
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
}

.day-header {
  font-size: 14px;
  padding: 5px;
}

.day-cell {
  padding: 7px;
  background-color: #f9f9f9;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 12px;
}

.day-cell.selected.today {
  background-color: #0d47a1;
  color: white;
}

.day-cell.inRange {
  background-color: #bbdefb;
}

.day-cell.today {
  background-color: #0d47a1;
  color: white;
}

.selected-week {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.selected-week span {
  background-color: #2196f3;
  color: white;
  padding: 5px;
  border-radius: 50%;
  width: 30px;
  text-align: center;
}
</style>