<script setup>
import { inject, ref } from 'vue';
import { useMyDashboardStore } from '@/stores/myspace/useMyDashboardStore';
import MonthlyComponent from '@/view/schedule/monthly/component/MonthlyComponent.vue';
import { monthlySettingUtils } from '@/utils/dateSettingUtils';
import { useCalendar } from '@/utils/calendarUtils';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Monthly Schedule';
contentsDescription.value = '나의 이달 일정을 살펴보세요!';

const mypageStore = useMyDashboardStore();

const { prevMonth, nextMonth } = useCalendar();
const { startDate, endDate } = monthlySettingUtils();
const currentStartDate = ref(startDate);
const currentEndDate = ref(endDate);

const fetchMonthlyData = async () => {
  await mypageStore.getMyMonthly({
    startDate: currentStartDate.value,
    endDate: currentEndDate.value
  });
};

const handlePrevMonth = () => {
  const {startDate: newStartDate, endDate: newEndDate} = prevMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  fetchMonthlyData();
};

const handleNextMonth = () => {
  const {startDate: newStartDate, endDate: newEndDate} = nextMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  fetchMonthlyData();
};

fetchMonthlyData();
</script>

<template>
  <div class="monthly">
    <MonthlyComponent
        :startDate="currentStartDate"
        :endDate="currentEndDate"
        :meetingData="mypageStore.mySprintData.meetings"
        :sprintData="mypageStore.mySprintData.sprints"
        @prevMonth="handlePrevMonth"
        @nextMonth="handleNextMonth"
    />
  </div>
</template>

<style scoped>
.monthly {
  padding: 20px;
}
</style>