<script setup>
import { inject } from 'vue';
import TaskColumn from './component/KanbanColumn.vue';
import { useTaskStore} from "@/stores/scrum/useTaskStore";
import {useRoute} from "vue-router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Kanban';
contentsDescription.value = '나의 태스크를 살펴보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const tasks = useTaskStore().getAllTask(workspaceId);
</script>

<template>
  <div class="kanban-board">
    <div>

    </div>
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