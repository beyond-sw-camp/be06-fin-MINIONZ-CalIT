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
const selectedWeek = ref(mypageStore.myWeeklyData);
const myWeeklyMeeting = ref(selectedWeek.value.meetings);
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
  selectedWeek.value = mypageStore.myWeeklyData;
  myWeeklyMeeting.value = selectedWeek.value.meetings;
};

onMounted(() => {
  fetchWeeklyData();
});
</script>

<template>
  <div class="weekly">
    <WeeklyComponent
        :selected-week="selectedWeek"
        @update:selected-week="updateSelectedWeek"
        :meeting-data="myWeeklyMeeting"
        :sprint-data="mypageStore.mySprintData.sprints"
    />
    <div class="week-data">
      <MiniCalendar @update:selected-week="updateSelectedWeek"/>
      <div class="mini-lists">
        <WeeklyIssues
            :selected-week="selectedWeek"
            :meetings="myWeeklyMeeting"
            :issues="mypageStore.myWeeklyData.issues"
        />
        <WeeklyTasks
            :selected-week="selectedWeek"
            :tasks="mypageStore.myWeeklyData.tasks"
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