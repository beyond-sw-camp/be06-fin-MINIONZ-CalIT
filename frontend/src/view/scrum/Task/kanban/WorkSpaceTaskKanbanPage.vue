<script setup>
import { inject, ref, watch, onMounted, computed } from 'vue';
import TaskColumn from './component/KanbanColumn.vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useRoute } from 'vue-router';

const reorderTasksByStatus = (tasksArray) => {
  if (!tasksArray) return [];

  const reorderedTasks = tasksArray.map((statusObject) => {
    const [status, tasks] = Object.entries(statusObject)[0];
    return { [status]: tasks };
  });

  return reorderedTasks;
};

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Kanban';
contentsDescription.value = '워크스페이스의 태스크를 살펴보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const taskStore = useTaskStore();
const tasksByStatus = ref(null);

onMounted(() => {
  tasksByStatus.value = taskStore.taskList;
});

watch(
  () => taskStore.taskList,
  (newTaskList) => {
    tasksByStatus.value = newTaskList;
  },
  { immediate: true }
);

const hasTasks = computed(() => {
  return tasksByStatus.value.some(
    (statusObject) => Object.values(statusObject)[0].length > 0
  );
});
</script>

<template>
  <div class="kanban-container">
    <div class="kanban-board" v-if="hasTasks">
      <TaskColumn
        v-for="task in reorderTasksByStatus(tasksByStatus)"
        :key="task.key"
        :data="task"
      />
    </div>
    <div class="initial-wrap" v-else>
      <p>태스크를 추가하고 일정 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/${workspaceId}/scrum/task/create`"
        >태스크 추가하기</router-link
      >
    </div>
  </div>
</template>

<style scoped>
.kanban-board {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
  overflow-x: auto;
}

.initial-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;

  a {
    padding: 10px 20px;
    background-color: #93aafd;
    color: white;
    border-radius: 5px;
    text-decoration: none;

    &:hover {
      background-color: #6f8ffc;
    }
  }
}
</style>
