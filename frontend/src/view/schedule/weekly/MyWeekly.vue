<script setup>
import {inject} from "vue";
import WeeklyComponent from "@/view/schedule/weekly/component/WeeklyComponent.vue";
import WeeklyScheduleComponent from "@/view/schedule/weekly/component/WeeklyScheduleComponent.vue";
import WeeklyTask from "@/view/schedule/weekly/component/WeeklyTask.vue";
// import MonthlyComponent from "@/view/schedule/monthly/component/MonthlyComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Monthly';
contentsDescription.value = '나의 이달 일정을 살펴보세요!';
</script>

<template>
  <div class="monthly">
    <WeeklyComponent/>
    <WeeklyScheduleComponent/>
    <WeeklyTask/>
  </div>
</template>

<style scoped>
.monthly{
  padding: 20px;
}
</style>