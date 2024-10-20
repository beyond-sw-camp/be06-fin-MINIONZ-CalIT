<script setup>
import { inject, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useWorkspaceDashboardStore } from '@/stores/workspace/useWorkspaceDashboardStore';
import WeeklyComponent from '@/view/schedule/weekly/component/WeeklyComponent.vue';
import WeeklyScheduleComponent from '@/view/schedule/weekly/component/WeeklyIssues.vue';
import WeeklyTask from '@/view/schedule/weekly/component/WeeklyTasks.vue';
import MiniCalendar from '@/view/schedule/weekly/component/MiniCalendar.vue';
import { formatUtil } from '@/utils/scheduleDateFnsUtils';
// import { useCalendar } from '@/utils/calendarUtils';
import { weeklySettingUtils } from '@/utils/scheduleDateSettingUtils';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Work Space Weekly';
contentsDescription.value = '워크스페이스의 이번주 일정을 살펴보세요!';

const dashboardStore = useWorkspaceDashboardStore();
const selectedWeek = ref(dashboardStore.workspaceWeeklyData);
const updateSelectedWeek = (week) => {
  selectedWeek.value = week;
};

const { startDate, endDate } = weeklySettingUtils();
const currentStartDate = ref(startDate);
const currentEndDate = ref(endDate);

const fetchWeeklyData = async () => {
  const formattedStartDate = formatUtil(currentStartDate.value, 'yyyy-MM-dd\'T\'HH:mm:ss');
  const formattedEndDate = formatUtil(currentEndDate.value, 'yyyy-MM-dd\'T\'HH:mm:ss');
  await dashboardStore.getWorkspaceWeekly({
    workspaceId,
    startDate: formattedStartDate,
    endDate: formattedEndDate,
  });
  selectedWeek.value = dashboardStore.workspaceWeeklyData;
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

watch(
    () => route.query.workspaceId,
    (newId) => {
      dashboardStore.getWorkspaceWeekly({
        workspaceId: newId,
        startDate: formatUtil(currentStartDate.value, 'yyyy-MM-dd\'T\'HH:mm:ss'),
        endDate: formatUtil(currentEndDate.value, 'yyyy-MM-dd\'T\'HH:mm:ss'),
      });
    }
);
</script>

<template>
  <div class="weekly">
    <WeeklyComponent
        :selected-week="selectedWeek"
        @update:selected-week="updateSelectedWeek"
        :meeting-data="dashboardStore.workspaceWeeklyData.meetings"
        :sprint-data="dashboardStore.workspaceWeeklyData.sprints"
        @prevWeek="handlePrevWeek"
        @nextWeek="handleNextWeek"
    />
    <div class="week-data">
      <MiniCalendar @update:selectedWeek="updateSelectedWeek"/>
      <div class="mini-lists">
        <WeeklyScheduleComponent
            :selected-week="selectedWeek"
            :issues="dashboardStore.workspaceWeeklyData.issues"
        />
        <WeeklyTask
            :selected-week="selectedWeek"
            :tasks="dashboardStore.workspaceWeeklyData.tasks"
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