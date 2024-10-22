<script setup>
import {inject, onMounted, ref} from 'vue';
import {useMyDashboardStore} from '@/stores/myspace/useMyDashboardStore';
import WeeklyComponent from '@/view/schedule/weekly/component/WeeklyComponent.vue';
import WeeklyIssues from '@/view/schedule/weekly/component/WeeklyIssues.vue';
import WeeklyTasks from '@/view/schedule/weekly/component/WeeklyTasks.vue';
import MiniCalendar from '@/view/schedule/weekly/component/MiniCalendar.vue';
import {formatUtil} from '@/utils/scheduleDateFnsUtils';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Space Weekly';
contentsDescription.value = '나의 이번주 일정을 살펴보세요!';

const mypageStore = useMyDashboardStore();
const selectedWeek = ref(mypageStore.mySprintData);
const updateSelectedWeek = (week) => {
  selectedWeek.value = week;
};

const currentStartDate = ref(new Date());
const currentEndDate = ref(new Date());

const fetchWeeklyData = async () => {
  const formattedStartDate = formatUtil(currentStartDate.value, 'yyyy-MM-dd\'T\'HH:mm:ss');
  const formattedEndDate = formatUtil(currentEndDate.value, 'yyyy-MM-dd\'T\'HH:mm:ss');
  await mypageStore.getMyWeekly({
    startDate: formattedStartDate,
    endDate: formattedEndDate,
  });
  selectedWeek.value = mypageStore.mySprintData;
};

const handlePrevWeek = async () => {
  const prevDate = new Date(currentStartDate.value);
  prevDate.setDate(prevDate.getDate() - 7);
  currentStartDate.value = prevDate;
  currentEndDate.value = new Date(prevDate.getFullYear(), prevDate.getMonth(), prevDate.getDate() + 6);
  await fetchWeeklyData();
};

const handleNextWeek = async () => {
  const nextDate = new Date(currentStartDate.value);
  nextDate.setDate(nextDate.getDate() + 7);
  currentStartDate.value = nextDate;
  currentEndDate.value = new Date(nextDate.getFullYear(), nextDate.getMonth(), nextDate.getDate() + 6);
  await fetchWeeklyData();
};

onMounted(() => {
  fetchWeeklyData();
});
</script>

<template>
  <div class="weekly">
    <WeeklyComponent
        :start-date="currentStartDate"
        :end-date="currentEndDate"
        :selected-week="selectedWeek"
        @update:selected-week="updateSelectedWeek"
        :meeting-data="mypageStore.mySprintData.meetings"
        :sprint-data="mypageStore.mySprintData.sprints"
        @prevWeek="handlePrevWeek"
        @nextWeek="handleNextWeek"
    />
    <div class="week-data">
      <MiniCalendar @update:selected-week="updateSelectedWeek"/>
      <div class="mini-lists">
        <WeeklyIssues
            :selected-week="selectedWeek"
            :issues="mypageStore.mySprintData.issues"
        />
        <WeeklyTasks
            :selected-week="selectedWeek"
            :tasks="mypageStore.mySprintData.tasks"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.weekly {
  padding: 30px;
  height: 85vh;
  display: flex;
  flex-direction: column;
  gap: 10px;
  justify-content: space-between;
  box-sizing: border-box;
}

.week-data {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: flex-end;
}

.mini-lists {
  width: calc(100% - 350px);
  display: flex;
  height: 235px;
  gap: 10px;
}
</style>