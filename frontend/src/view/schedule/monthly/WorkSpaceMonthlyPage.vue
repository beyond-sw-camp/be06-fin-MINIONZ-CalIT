<script setup>
import {inject, onMounted, ref, watch} from 'vue';
import { useWorkspaceDashboardStore } from '@/stores/workspace/useWorkspaceDashboardStore';
import MonthlyComponent from '@/view/schedule/monthly/component/MonthlyComponent.vue';
import { useRoute } from 'vue-router';
import { monthlySettingUtils } from '@/utils/scheduleDateSettingUtils';
import { useCalendar } from '@/utils/calendarUtils';
import {useWorkspaceStore} from "@/stores/workspace/useWorkspaceStore";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Monthly Schedule';
contentsDescription.value = '워크스페이스의 이달 일정을 살펴보세요!';

const dashboardStore = useWorkspaceDashboardStore();

const route = useRoute();
const workspaceStore = useWorkspaceStore();
const { setWorkspaceId } = workspaceStore;

const { prevMonth, nextMonth } = useCalendar();
const { startDate, endDate } = monthlySettingUtils();
const currentStartDate = ref(startDate);
const currentEndDate = ref(endDate);

const fetchWorkspaceMonthlyData = async (workspaceId) => {
  await dashboardStore.getWorkspaceMonthly({
    workspaceId,
    startDate: currentStartDate.value,
    endDate: currentEndDate.value
  });
};

const handlePrevMonth = async () => {
  const {startDate: newStartDate, endDate: newEndDate} = prevMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  await fetchWorkspaceMonthlyData();
};

const handleNextMonth = async () => {
  const {startDate: newStartDate, endDate: newEndDate} = nextMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  await fetchWorkspaceMonthlyData();
};

watch(() => route.query.wsId, (newId) => {
  setWorkspaceId(newId);
  fetchWorkspaceMonthlyData(newId);
});

onMounted(() => {
  if (route.query.wsId) {
    setWorkspaceId(route.query.wsId);
    fetchWorkspaceMonthlyData(route.query.wsId);
  }
});
</script>

<template>
  <div class="monthly">
    <MonthlyComponent
        :startDate="currentStartDate"
        :endDate="currentEndDate"
        :meeting-data="dashboardStore.workspaceMonthlyData.meetings"
        :sprint-data="dashboardStore.workspaceMonthlyData.sprints"
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