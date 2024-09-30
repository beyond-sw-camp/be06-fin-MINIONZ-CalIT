<script setup>
import ListItem from './ListItem.vue';
import { computed, defineProps, ref, watch } from 'vue';
import { VueDraggableNext } from 'vue-draggable-next';
// import { getTaskCountBackgroundColor, getTaskCountColor } from '@/utils/taskUtils';

const props = defineProps({
  data: {
    type: Array,
    required: true
  }
});

const getTaskCountBackgroundColor = (status) => {
  return getTaskCountBackgroundColor(status);
}
const getTaskCountColor = (status) => {
  return getTaskCountColor(status);
}

const taskCountBgStyle = computed(() => {
  return getTaskCountBackgroundColor(props.data.status);
});

const taskCountColorStyle = computed(() => {
  return getTaskCountColor(props.data.status);
});

const tasks = ref([...props.data.tasks]);

watch(() => props.data.tasks, (newTasks) => {
  tasks.value = [...newTasks];
});
</script>

<template>
  <div class="task-column">
    <div class="column-header">
      <div class="column-title">
        <p>{{ data.status }}</p>
        <span class="task-count" :style="{ backgroundColor: taskCountBgStyle, color: taskCountColorStyle }">{{ tasks.length }}</span>
      </div>
      <div class="add-task-card">
        <span class="plus">+</span> <span class="add-text">Add Task</span>
      </div>
    </div>
    <VueDraggableNext :list="tasks" item-key="id" group="tasks" draggable=".task-card" handle=".task-card">
      <ListItem v-for="(task) in tasks" :key="task.id" :task="task"/>
    </VueDraggableNext>
  </div>
</template>

<style scoped>
.task-column {
  background-color: #fff;
  padding: 20px;
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 10px 5px;
}

.column-title{
  display: flex;
  align-items: center;
  gap: 10px;
}

p {
font-size: 14px;
font-weight: 500;
}

.task-count {
  padding: 0 10px;
  border-radius: 30px;
}
</style>