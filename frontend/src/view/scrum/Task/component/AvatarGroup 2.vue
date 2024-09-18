<script setup>
import { defineProps } from 'vue';

defineProps({
  avatars: {
    type: Array,
    required: true
  }
});
</script>

<template>
  <div class="task-avatars">
    <img v-for="(avatar, index) in avatars" :key="index" :src="avatar" class="avatar" alt="Avatar" />
  </div>
</template>

<style scoped>
.task-avatars {
  display: flex;
  margin-right: 10px;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-left: -10px;
  border: 2px solid #fff;
}
</style>