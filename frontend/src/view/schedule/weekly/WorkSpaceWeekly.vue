<script setup>
import {inject, ref} from "vue";
import WeeklyComponent from "@/view/schedule/weekly/component/WeeklyComponent.vue";
import WeeklyScheduleComponent from "@/view/schedule/weekly/component/WeeklyScheduleComponent.vue";
import WeeklyTask from "@/view/schedule/weekly/component/WeeklyTask.vue";
import MiniCalendar from "@/view/schedule/weekly/component/MiniCalendar.vue";
// import MonthlyComponent from "@/view/schedule/monthly/component/MonthlyComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Work Space Monthly';
contentsDescription.value = '나의 이달 일정을 살펴보세요!';

const selectedWeek = ref([new Date()]);
const updateSelectedWeek = (week) => {
  selectedWeek.value = week;
};
</script>

<template>
  <div class="weekly">
    <WeeklyComponent :selected-week="selectedWeek" @update:selected-week="updateSelectedWeek"/>
    <div class="week-data">
      <MiniCalendar @update:selectedWeek="updateSelectedWeek"/>
      <div class="mini-lists">
        <WeeklyScheduleComponent/>
        <WeeklyTask/>
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