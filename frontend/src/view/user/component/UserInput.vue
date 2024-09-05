<script setup>
import { defineProps } from 'vue';

defineProps({
  label: {
    type: String,
    required: true
  },
  // inputValue: {
  //   type: String,
  //   required: true
  // },
  inputPlaceholder: {
    type: String,
    required: true
  }
});
</script>

<template>
  <div class="inputWrap">
    <label for="username">{{ label }}</label>
    <input type="text" :placeholder="inputPlaceholder"/>
  </div>
</template>

<style scoped>
.inputWrap{
  width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}
label{
  font-size: 14px;
  color: #5a607f;
}
input{
  width: 100%;
  height: 48px;
  border: 1px solid #d9e1ec;
  border-radius: 4px;
  padding: 0 15px;
  box-sizing: border-box;
}
</style>