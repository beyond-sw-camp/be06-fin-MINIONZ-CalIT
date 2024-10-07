<script setup>
import { inject, ref } from "vue";
import { useMyDashboardStore } from "@/stores/myspace/useMyDashboardStore";
import WeeklyComponent from "@/view/schedule/weekly/component/WeeklyComponent.vue";
import WeeklyIssues from "@/view/schedule/weekly/component/WeeklyIssues.vue";
import WeeklyTasks from "@/view/schedule/weekly/component/WeeklyTasks.vue";
import MiniCalendar from "@/view/schedule/weekly/component/MiniCalendar.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Space Weekly';
contentsDescription.value = '나의 이번주 일정을 살펴보세요!';

const myWeek = ref({
  sprints: [],
  meetings: [],
  tasks: [],
  issues: []
});

const mypageStore = useMyDashboardStore();
const selectedWeek = ref(mypageStore.getMyWeekly());
const myWeeklyMeeting = ref(selectedWeek.value.meetings);
const updateSelectedWeek = (week) => {
  selectedWeek.value = week;
};
</script>

<template>
  <div class="weekly">
    <WeeklyComponent :selected-week="selectedWeek" @update:selected-week="updateSelectedWeek"
                     :sprints="myWeek.sprints" :meetings="myWeek.meetings"/>
    <div class="week-data">
      <MiniCalendar @update:selected-week="updateSelectedWeek"/>
      <div class="mini-lists">
        <WeeklyIssues :selected-week="selectedWeek" :meetings="myWeeklyMeeting" :issues="myWeek.issues"/>
        <WeeklyTasks :selected-week="selectedWeek" :tasks="myWeek.tasks"/>
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