<script setup>
import { computed, defineProps, watch } from 'vue';
import { VueDraggableNext } from 'vue-draggable-next';
import TaskCard from './KanbanCard.vue';
import {
  getTaskCountBackgroundColor,
  getTaskCountColor,
} from '@/utils/taskUtils';
import { useRoute } from 'vue-router';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
});

const taskCountBgStyle = computed(() => {
  return getTaskCountBackgroundColor(props.data.status);
});

const taskCountColorStyle = computed(() => {
  return getTaskCountColor(props.data.status);
});

const status = computed(() => Object.keys(props.data)[0]);
const tasks = computed(() => Object.values(props.data)[0]);

watch(
  () => props.data.tasks,
  (newTasks) => {
    tasks.value = [...newTasks];
  }
);
</script>

<template>
  <div class="task-column">
    <div class="column-header">
      <p>{{ status }}</p>
      <span
        class="task-count"
        :style="{
          backgroundColor: taskCountBgStyle,
          color: taskCountColorStyle,
        }"
      >
        {{ tasks.length }}
      </span>
    </div>

    <!-- 드래그 가능한 Task 카드들 -->
    <VueDraggableNext
      :list="tasks"
      item-key="id"
      group="tasks"
      draggable=".task-card"
      handle=".task-card"
    >
      <TaskCard v-for="task in tasks" :key="task.id" :task="task" />
    </VueDraggableNext>

    <router-link
      :to="`/workspace/${workspaceId}/scrum/task/create`"
      class="add-task-card"
    >
      <span class="plus">+</span> <span class="add-text">Add Task</span>
    </router-link>
  </div>
</template>

<style scoped>
.task-column {
  background-color: #f9f9f9;
  padding: 10px;
  border-radius: 5px;
  width: 250px;
}
.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.add-task-card {
  display: flex;
  align-items: center;
  margin-top: 10px;
  text-decoration: none;
  color: #0066cc;
}
</style>
