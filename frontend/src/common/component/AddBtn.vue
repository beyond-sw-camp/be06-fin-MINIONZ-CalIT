<script setup>
import { defineProps } from 'vue';

defineProps({
  btn_txt: String
});
</script>

<template>
<button>{{btn_txt}}</button>
</template>

<style scoped>
button{
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}
</style>