<script setup>
import { inject } from 'vue';
import { taskData } from '@/static/taskData';
import TaskColumn from './component/KanbanColumn.vue';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Kanban';
contentsDescription.value = '워크스페이스의 태스크를 살펴보세요!';

const tasks = taskData;
</script>

<template>
  <div class="kanban-board">
    <TaskColumn v-for="task in tasks" :key="task.id" :data="task"/>
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