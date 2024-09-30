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
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  margin-bottom: 10px;
  cursor: pointer;
}
</style>