<script setup>
import { defineProps } from 'vue';

defineProps({
  buttonMent: {
    type: String,
    required: true
  }
});
</script>

<template>
  <button>{{ buttonMent }}</button>
</template>

<style scoped>
button {
  background-color: #1E5EFF;
  color: white;
  border: none;
  border-radius: 4px;
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 14px;
  margin-bottom: 10px;
  cursor: pointer;
}
</style>