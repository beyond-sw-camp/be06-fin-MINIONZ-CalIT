<script setup>
import { inject, ref, watch, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import ListContainer from './component/ListContainer.vue';
import { useSprintStore } from '@/stores/scrum/useSprintStore';

const sprintStore = useSprintStore();

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace Kanban';
contentsDescription.value = '워크스페이스의 태스크를 살펴보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;
const isLoading = ref(false);

const taskStore = useTaskStore();
const tasksByStatus = ref(null);

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
  <div class="list">
    <div v-if="isLoading" class="loading-message">
      나의 칸반을 조회하는 중입니다..
    </div>
    <div v-else>
      <div v-if="hasTasks">
        <ListContainer
          v-for="task in reorderTasksByStatus(tasksByStatus)"
          :key="task.key"
          :data="task"
          @taskUpdated="fetchTasks"
        />
      </div>
      <div v-else class="initial-wrap">
        <p>태스크를 추가하고 일정 관리를 시작해보세요!</p>
        <router-link :to="`/workspace/${workspaceId}/scrum/task/create`"
          >태스크 추가하기</router-link
        >
      </div>
    </div>
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
