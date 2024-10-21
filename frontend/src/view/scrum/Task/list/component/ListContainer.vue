<script setup>
import ListItem from './ListItem.vue';
import {computed, defineEmits, defineProps, watch} from 'vue';
import { VueDraggableNext } from 'vue-draggable-next';
import { getTaskCountBackgroundColor, getTaskCountColor,} from '@/utils/taskUtils';
import {useRoute} from "vue-router";
import {useSprintStore} from "@/stores/scrum/useSprintStore";
import {useTaskStore} from "@/stores/scrum/useTaskStore";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const sprintStore = useSprintStore();
const taskStore = useTaskStore();

const emit = defineEmits(['taskUpdated']);

const props = defineProps({
  data: {
    type: Object,
    required: true,
  },
});

const taskCountBgStyle = computed(() => {
  console.log(props.status);
  console.log(props);
  return getTaskCountBackgroundColor(props.status);
});

const taskCountColorStyle = computed(() => {
  return getTaskCountColor(props.status).color;
});

const status = computed(() => Object.keys(props.data)[0]);
const tasks = computed(() => Object.values(props.data)[0]);

watch(
  () => props.data.tasks,
  (newTasks) => {
    tasks.value = [...newTasks];
  }
);

const handleDragEnd = async (event) => {
  const newStatus = event.to.getAttribute('item-key');
  const taskId = event.item.getAttribute('id');

  await taskStore.updateTaskStatus(sprintStore.nowSprintId, taskId, newStatus);

  emit('taskUpdated');
};
</script>

<template>
  <div class="task-column">
    <div class="column-header">
      <div class="column-title">
        <p>{{ getTaskCountColor(status).text }}</p>
        <span
          class="task-count"
          :style="{
            backgroundColor: taskCountBgStyle,
            color: taskCountColorStyle,
          }"
          >{{ tasks.length }}</span
        >
      </div>
      <div class="add-task-card">
        <router-link v-if="!route.path.startsWith('/my')" :to='`/workspace/${workspaceId}/scrum/task/create`'>
          <span class="plus">+</span> <span class="add-text">Add Task</span>
        </router-link>
      </div>
    </div>

    <VueDraggableNext
        v-if="tasks && tasks.length > 0"
        :list="tasks"
        :item-key="status"
        group="tasks"
        draggable=".task-card"
        handle=".task-card"
        @end="handleDragEnd"
    >
      <ListItem
          v-for="task in tasks"
          :key="task.id"
          :task="task"
          :id="task.id"
      />
    </VueDraggableNext>
    <p v-else class="task-card">현재 할당된 task가 없습니다.</p>
  </div>
</template>

<style scoped>
.task-column {
  background-color: #fff;
  padding: 20px;
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 0 10px 5px;
}

.column-title {
  display: flex;
  align-items: center;
  gap: 10px;
}

p {
  font-size: 14px;
  font-weight: 500;
}

.task-count {
  padding: 0 10px;
  border-radius: 30px;
}

.task-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: #f7f8fa;
  border-radius: 8px;
  margin-bottom: 15px;
}
</style>
