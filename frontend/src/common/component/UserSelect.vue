<template>
  <div>
    <label :for="id">{{ label }}</label>
    <select :id="id" v-model="selectedUser" @focus="fetchUsers" @change="emitSelection" class="input-field">
      <option v-for="user in users" :key="user.id" :value="user">{{ user.name }}</option>
    </select>
  </div>
</template>

<script setup>
import {ref, watch, defineProps, defineEmits} from 'vue';

const props = defineProps({
  id: String,
  label: String,
  users: Array,
  modelValue: Object,
});

const emit = defineEmits(['update:modelValue', 'fetch-users']);

const selectedUser = ref(props.modelValue);

watch(selectedUser, (newValue) => {
  emit('update:modelValue', newValue);
});

const emitSelection = () => {
  emit('update:modelValue', selectedUser.value);
};

const fetchUsers = () => {
  emit('fetch-users');
};
</script>

<style scoped>
.input-field {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}
</style>