<script setup>
import {inject} from "vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = '오늘의 점심';
contentsDescription.value = '포크포케!!';
</script>

<template>
  <ul style="margin-top: 20px">
    <li>다윤: 치킨 포케 -> 밥은 반만, 국 필요 없음</li>
    <li>가은: 치킨 포케 -> 밥은 반만</li>
    <li>은주: 포크 포케 -> 밥은 반만 국 필요없음</li>
    <li>승은: 치킨 포케 -> 밥은 반만 국 필요없음</li>
  </ul>
</template>

<style scoped>

</style>