<script setup>
import {inject, onMounted} from 'vue';
import TaskColumn from './component/KanbanColumn.vue';
import { useTaskStore } from "@/stores/scrum/useTaskStore";
import { useRoute } from "vue-router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Kanban';
contentsDescription.value = '워크스페이스의 태스크를 살펴보세요!';

const route = useRoute();
const sprintId = route.params.sprintId;
const workspaceId = route.params.workspaceId;

const taskStore = useTaskStore();

onMounted(() => {
  taskStore.getTaskList(sprintId);
})
</script>

<template>
  <div class="kanban-board">
    <div v-if="taskStore && taskStore.taskData && taskStore.taskData.length > 0">
      <TaskColumn v-for="task in tasks" :key="task.id" :data="task"/>
    </div>
    <div v-else class="initial-wrap">
      <p>태스크를 추가하고 일정 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/${workspaceId}/scrum/task/create`">태스크 추가하기</router-link>
    </div>
  </div>
</template>

<style scoped>
.initial-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;

  a {
    padding: 10px 20px;
    background-color: #93AAFD;
    color: white;
    border-radius: 5px;
    text-decoration: none;

    &:hover {
      background-color: #6F8FFC;
    }
  }
}
</style>