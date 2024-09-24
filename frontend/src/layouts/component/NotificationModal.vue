<script setup>
import { useAlarmStore } from '@/stores/socket/useAlarmStore';

const alarmStore = useAlarmStore();
</script>

<template>
  <div>
    <div v-for="alarm in alarmStore.alarms" :key="alarm.id">
      {{ alarm.message }}
    </div>
  </div>
</template>

<style scoped>
  div {
    position: fixed;
    top: 0;
    right: 0;
    background-color: white;
    border: 1px solid black;
    padding: 10px;
  }
</style>