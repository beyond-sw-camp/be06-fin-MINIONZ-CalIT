<script setup>
import {inject, ref, onMounted} from 'vue';
import {useWorkspaceDashboardStore} from '@/stores/workspace/useWorkspaceDashboardStore';
import MonthlyComponent from '@/view/schedule/monthly/component/MonthlyComponent.vue';
import {useRoute} from 'vue-router';
import {monthlySettingUtils} from '@/utils/dateSettingUtils';
import {useCalendar} from '@/utils/calendarUtils';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Monthly Schedule';
contentsDescription.value = '워크스페이스의 이달 일정을 살펴보세요!';

const dashboardStore = useWorkspaceDashboardStore();
const route = useRoute();
const workspaceId = route.params.workspaceId;

const {startDate, endDate} = monthlySettingUtils();
const currentStartDate = ref(startDate);
const currentEndDate = ref(endDate);

const {prevMonth, nextMonth} = useCalendar();

const fetchWorkspaceMonthlyData = async () => {
  await dashboardStore.getWorkspaceMonthly({
    workspaceId,
    startDate: currentStartDate.value,
    endDate: currentEndDate.value
  });
};

const workspaceMonthlyMeeting = ref([]);

onMounted(async () => {
  await fetchWorkspaceMonthlyData();
  workspaceMonthlyMeeting.value = dashboardStore.workspaceMonthlyData.meetings || [];
  console.log(workspaceMonthlyMeeting.value);
});

const handlePrevMonth = async () => {
  const {startDate: newStartDate, endDate: newEndDate} = prevMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  await fetchWorkspaceMonthlyData();
  workspaceMonthlyMeeting.value = dashboardStore.workspaceMonthlyData.meetings || [];
};

const handleNextMonth = async () => {
  const {startDate: newStartDate, endDate: newEndDate} = nextMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  await fetchWorkspaceMonthlyData();
  workspaceMonthlyMeeting.value = dashboardStore.workspaceMonthlyData.meetings || [];
};
</script>

<template>
  <div class="monthly">
    <MonthlyComponent
        :startDate="currentStartDate"
        :endDate="currentEndDate"
        :meetings="workspaceMonthlyMeeting"
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