<script setup>
import { ref, computed, defineEmits } from 'vue';
import { startOfMonth, format, getDay, getDaysInMonth, addMonths, subMonths, addDays, startOfWeek, endOfWeek, isSameDay } from 'date-fns';

const emit = defineEmits(['update:selectedWeek']);

const today = new Date();
const currentYear = ref(today.getFullYear());
const currentMonth = ref(today.getMonth());
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

// const formatDate = (date, formatStr) => format(date, formatStr);
</script>

<template>
  <div class="calendar-container">
    <!-- 상단 네비게이션 (월 이동) -->
    <div class="calendar-header">
      <span>{{ currentMonthLabel }}</span>
      <div>
        <button @click="prevMonth">◀</button>
        <button @click="nextMonth">▶</button>
      </div>
    </div>

    <!-- 요일 표시 -->
    <div class="calendar-grid header">
      <div v-for="day in weekDays" :key="day" class="day-header">{{ day }}</div>
    </div>

    <!-- 날짜 표시 -->
    <div class="calendar-grid">
      <div v-for="n in startBlankDays" :key="n"></div>
      <div
          v-for="day in daysInMonth"
          :key="day"
          class="day-cell"
          :class="{ selected: isSelected(day), inRange: isInRange(day) }"
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
  //margin-bottom: 20px;
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

.day-cell.selected {
  background-color: #2196f3;
  //color: white;
  //border-radius: 50%;
}

.day-cell.inRange {
  background-color: #bbdefb;
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