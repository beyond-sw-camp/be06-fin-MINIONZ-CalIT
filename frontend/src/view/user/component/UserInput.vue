<script setup>
import { defineProps, defineEmits } from 'vue';

defineProps({
  modelValue: String,
  inputPlaceholder: String,
  label: String,
  type: String
});

const emit = defineEmits(['update:modelValue']);

const updateValue = (event) => {
  emit('update:modelValue', event.target.value);
};
</script>

<template>
  <div class="user-input">
    <label>{{ label }}</label>
    <input :placeholder="inputPlaceholder" :value="modelValue" @input="updateValue" :type="type"/>
  </div>
</template>

<style scoped>
.user-input{
  width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
}
label{
  font-size: 12px;
  color: #5a607f;
  margin-bottom: 5px;
}
input{
  width: 100%;
  height: 32px;
  border: 1px solid #d9e1ec;
  border-radius: 4px;
  padding: 0 10px;
  box-sizing: border-box;
  font-size: 12px;
}

input::placeholder {
  font-size: 12px;
}
</style>