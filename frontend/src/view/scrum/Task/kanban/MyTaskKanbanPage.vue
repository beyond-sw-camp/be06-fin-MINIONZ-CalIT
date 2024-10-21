<script setup>
import {inject, onMounted, ref, watch} from 'vue';
import TaskColumn from './component/KanbanColumn.vue';
import { useTaskStore } from "@/stores/scrum/useTaskStore";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Kanban';
contentsDescription.value = '나의 태스크를 살펴보세요!';

const tasks = ref(null);
const taskStore = useTaskStore();
const tasksByStatus = ref(null);

const reorderTasksByStatus = (tasksArray) => {
  if (!tasksArray) return [];

  const reorderedTasks = { TODO: [], IN_PROGRESS: [], DONE: [] };

  tasksArray.forEach((statusObject) => {
    const [status, tasks] = Object.entries(statusObject)[0];
    if (reorderedTasks[status] !== undefined) {
      reorderedTasks[status].push(...tasks);
    }
  });

  return [
    { TODO: reorderedTasks.TODO },
    { IN_PROGRESS: reorderedTasks.IN_PROGRESS },
    { DONE: reorderedTasks.DONE },
  ];
};

const fetchTasks = async () => {
  tasks.value = await taskStore.getMyTask()
};

onMounted(() => {
  fetchTasks();
});

watch(
    () => taskStore.taskList,
    (newTaskList) => {
      tasksByStatus.value = newTaskList;
    },
    { immediate: true }
);
</script>

<template>
  <div class="kanban-board">
    <TaskColumn v-for="task in reorderTasksByStatus(tasksByStatus)" :key="task.id" :data="task"/>
  </div>
</template>

<style scoped>
.kanban-board {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
  overflow-x: auto;
}
</style>