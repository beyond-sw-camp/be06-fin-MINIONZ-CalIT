<script setup>
import { inject, ref } from 'vue';
import { useWorkspaceDashboardStore } from '@/stores/workspace/useWorkspaceDashboardStore';
import MonthlyComponent from '@/view/schedule/monthly/component/MonthlyComponent.vue';
import { useRoute } from 'vue-router';
import { monthlySettingUtils } from '@/utils/dateSettingUtils';
import { useCalendar } from '@/utils/calendarUtils';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Monthly Schedule';
contentsDescription.value = '워크스페이스의 이달 일정을 살펴보세요!';

const dashboardStore = useWorkspaceDashboardStore();
const route = useRoute();
const workspaceId = route.params.workspaceId;

const { startDate, endDate } = monthlySettingUtils();
const currentStartDate = ref(startDate);
const currentEndDate = ref(endDate);

const { prevMonth, nextMonth } = useCalendar();

const fetchWorkspaceMonthlyData = () => {
  dashboardStore.getWorkspaceMonthly({ workspaceId, startDate: currentStartDate.value, endDate: currentEndDate.value });
};

const handlePrevMonth = () => {
  const { startDate: newStartDate, endDate: newEndDate } = prevMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  fetchWorkspaceMonthlyData();
};

const handleNextMonth = () => {
  const { startDate: newStartDate, endDate: newEndDate } = nextMonth(currentStartDate.value, currentEndDate.value);
  currentStartDate.value = newStartDate;
  currentEndDate.value = newEndDate;
  fetchWorkspaceMonthlyData();
};

fetchWorkspaceMonthlyData();
</script>

<template>
  <div class="monthly">
    <MonthlyComponent
        :startDate="currentStartDate"
        :endDate="currentEndDate"
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