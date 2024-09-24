<script setup>
import {inject} from "vue";
import MonthlyComponent from "@/view/schedule/monthly/component/MonthlyComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace  Monthly Schedule';
contentsDescription.value = '워크스페이스의 이달 일정을 살펴보세요!';
</script>

<template>
  <div class="monthly">
    <MonthlyComponent/>
  </div>
</template>

<style scoped>
.monthly{
  padding: 20px;
}
</style>