<script setup>
import { inject, ref, watch, onMounted, computed } from 'vue';
import TaskColumn from './component/KanbanColumn.vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useRoute } from 'vue-router';
import { useSprintStore } from '@/stores/scrum/useSprintStore';

const sprintStore = useSprintStore();
const isLoading = ref(false);

const reorderTasksByStatus = (tasksArray) => {
  if (!tasksArray) return [];

  const reorderedTasks = { NO_STATUS: [], TODO: [], IN_PROGRESS: [], DONE: [] };

  tasksArray.forEach((statusObject) => {
    const [status, tasks] = Object.entries(statusObject)[0];
    if (reorderedTasks[status] !== undefined) {
      reorderedTasks[status].push(...tasks);
    }
  });

  return [
    { NO_STATUS: reorderedTasks.NO_STATUS },
    { TODO: reorderedTasks.TODO },
    { IN_PROGRESS: reorderedTasks.IN_PROGRESS },
    { DONE: reorderedTasks.DONE },
  ];
};

const fetchTasks = async () => {
  isLoading.value = true;
  tasksByStatus.value = await taskStore.getTaskList(
    workspaceId,
    sprintStore.nowSprintId
  );
  isLoading.value = false;
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
      <div v-if="isLoading" class="loading-message">
        칸반을 조회하는 중입니다..
      </div>
      <TaskColumn
        v-else
        v-for="task in reorderTasksByStatus(tasksByStatus)"
        :key="task.key"
        :data="task"
        @taskUpdated="fetchTasks"
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

.loading-message {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 18px;
  color: #333;
}
</style>
