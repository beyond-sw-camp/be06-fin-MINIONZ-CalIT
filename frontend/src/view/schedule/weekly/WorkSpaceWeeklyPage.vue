<script setup>
import {inject, ref} from "vue";
import {useRoute} from "vue-router";
import { useWorkspaceDashboardStore } from "@/stores/workspace/useWorkspaceDashboardStore"
import WeeklyComponent from "@/view/schedule/weekly/component/WeeklyComponent.vue";
import WeeklyScheduleComponent from "@/view/schedule/weekly/component/WeeklyIssues.vue";
import WeeklyTask from "@/view/schedule/weekly/component/WeeklyTasks.vue";
import MiniCalendar from "@/view/schedule/weekly/component/MiniCalendar.vue";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Work Space Weekly';
contentsDescription.value = '워크스페이스의 이번주 일정을 살펴보세요!';

const dashboardStore = useWorkspaceDashboardStore();
const selectedWeek = ref(dashboardStore.getWorkspaceWeekly(workspaceId));
const updateSelectedWeek = (week) => {
  selectedWeek.value = week;
};
</script>

<template>
  <div class="weekly">
    <WeeklyComponent :selected-week="selectedWeek" @update:selected-week="updateSelectedWeek" />
    <div class="week-data">
      <MiniCalendar @update:selectedWeek="updateSelectedWeek"/>
      <div class="mini-lists">
        <WeeklyScheduleComponent :selected-week="selectedWeek" />
        <WeeklyTask :selected-week="selectedWeek"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
.weekly{
  padding: 30px;
  height: 85vh;
  display: flex;
  flex-direction: column;
  gap: 10px;
  justify-content: space-between;
  box-sizing: border-box;
}

.week-data{
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: flex-end;
}

.mini-lists{
  width: calc(100% - 350px);
  display: flex;
  height: 235px;
  gap: 10px;
}
</style>