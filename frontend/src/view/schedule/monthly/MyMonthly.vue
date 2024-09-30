<script setup>
import {inject} from "vue";
import { useMypageStore } from "@/stores/my/useMypageStore";
import MonthlyComponent from "@/view/schedule/monthly/component/MonthlyComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Monthly Schedule';
contentsDescription.value = '나의 이달 일정을 살펴보세요!';

const mypageStore = useMypageStore();
</script>

<template>
  <div class="monthly">
    <MonthlyComponent :data="mypageStore.mySprintData"/>
  </div>
</template>

<style scoped>
.monthly{
  padding: 20px;
}
</style>