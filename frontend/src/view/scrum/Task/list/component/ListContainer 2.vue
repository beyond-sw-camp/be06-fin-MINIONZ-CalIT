<script setup>
import ListItem from './ListItem.vue';
import { computed, defineProps, ref, watch } from 'vue';
import { VueDraggableNext } from 'vue-draggable-next';

const props = defineProps({
  data: {
    type: Array,
    required: true
  }
});

const getTaskCountBackgroundColor = (status) => {
  if (status === 'No Status') return 'rgba(234, 179, 8, 0.1)';
  else if (status === 'To Do') return 'rgba(236, 72, 153, 0.1)';
  else if (status === 'In Progress') return 'rgba(168, 85, 247, 0.1)';
  else return 'rgba(34, 197, 94, 0.1)';
}
const getTaskCountColor = (status) => {
  if (status === 'No Status') return 'rgba(234, 179, 8)';
  else if (status === 'To Do') return 'rgba(236, 72, 153)';
  else if (status === 'In Progress') return 'rgba(168, 85, 247)';
  else return 'rgba(34, 197, 94)';
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