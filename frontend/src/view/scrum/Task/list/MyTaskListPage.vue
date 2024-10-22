<script setup>
import { inject, onMounted, ref, watch } from 'vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import ListContainer from './component/MyListContainer.vue';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Kanban';
contentsDescription.value = '나의 태스크를 살펴보세요!';

const tasks = ref(null);
const taskStore = useTaskStore();
const tasksByStatus = ref(null);
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
  tasks.value = await taskStore.getMyTask();
  tasksByStatus.value = reorderTasksByStatus(tasks.value);
  isLoading.value = false;
};

onMounted(() => {
  fetchTasks();
});

watch(
  () => taskStore.taskList,
  (newTaskList) => {
    tasksByStatus.value = reorderTasksByStatus(newTaskList);
  },
  { immediate: true }
);
</script>

<template>
  <div class="list">
    <div v-if="isLoading" class="loading-message">
      나의 칸반을 조회하는 중입니다..
    </div>
    <ListContainer
      v-else
      v-for="task in tasksByStatus"
      :key="task.id"
      :data="task"
      @taskUpdated="fetchTasks"
    />
  </div>
</template>

<style scoped>
.list {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 20px;
  padding: 0 20px;
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
