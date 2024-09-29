<script setup>
import { computed, defineProps, ref, watch } from 'vue';
import { VueDraggableNext } from 'vue-draggable-next';
import TaskCard from './KanbanCard.vue';
import { getTaskCountBackgroundColor, getTaskCountColor } from '@/utils/taskUtils';
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;
const props = defineProps({
  data: {
    type: Object,
    required: true
  }
});

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
      <p>{{ data.status }}</p>
      <span class="task-count" :style="{ backgroundColor: taskCountBgStyle, color: taskCountColorStyle }">{{ tasks.length }}</span>
    </div>
    <VueDraggableNext :list="tasks" item-key="id" group="tasks" draggable=".task-card" handle=".task-card">
      <TaskCard v-for="(task) in tasks" :key="task.id" :task="task"/>
    </VueDraggableNext>
    <router-link :to="`/workspace/${workspaceId}/scrum/task/create`" class="add-task-card">
      <span class="plus">+</span> <span class="add-text">Add Task</span>
    </router-link>
  </div>
</template>

<style scoped>
a{
  text-decoration: none;
  color: #6b7280;
}
.task-column {
  width: 100%;
  background-color: #fff;
  border-radius: 8px;
  padding: 10px;
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 10px 5px;
}

p {
  font-size: 14px;
  font-weight: 500;
}

.task-count {
  padding: 0 10px;
  border-radius: 30px;
}

.add-task-card {
  background-color: #F7F8FA;
  border-radius: 8px;
  padding: 10px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
}

.plus {
  font-weight: bold;
  background-color: #C7CED9;
  color: #fff;
  padding: 0 7px;
  border-radius: 50%;
}

.add-text {
  font-size: 12px;
}
</style>