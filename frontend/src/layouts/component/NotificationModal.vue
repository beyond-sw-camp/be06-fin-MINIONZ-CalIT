<script setup>
import { useNotificationStore } from '@/store/socket/useNotifyStore';

const notificationStore = useNotificationStore();
</script>

<template>
  <div>
    <div v-for="notification in notificationStore.notifications" :key="notification.id">
      {{ notification.message }}
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