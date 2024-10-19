<script setup>
import { setPersona } from '@/utils/personaUtils';
import { defineProps } from 'vue';
import { formatDate } from '@/utils/timeUtils';
import router from "@/router";
import {useRoute} from "vue-router";

defineProps({
  task: {
    type: Object,
    required: true,
  },
});

const route = useRoute();
const workspaceId = route.params.workspaceId;

function navigateToDetailPage(task) {
  router.push(`/workspace/${workspaceId}/scrum/task/${task.id}`);
}
</script>

<template>
  <div class="task-card">
    <p @click="navigateToDetailPage(task)">{{ task.title }}</p>
    <div class="labels">
      <span v-for="label in task.labels" :key="label" class="label">{{
        label.labelName
      }}</span>
    </div>
    <div class="task-footer">
      <div class="avatars">
        <img
          v-for="user in task.participants"
          :key="user.id"
          :src="setPersona(user.persona)"
          class="avatar"
          alt="users"
        />
        <span v-if="task.participants.length > 3" class="more">and more</span>
      </div>
      <span class="due-date">{{ formatDate(task.endDate) }}</span>
    </div>
  </div>
</template>

<style scoped>
.task-card {
  background-color: #f7f8fa;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
}

p {
  font-size: 12px;
  font-weight: 500;
}

.labels {
  margin: 10px 0;
}

.label {
  background-color: #e0f7fa;
  padding: 5px 10px;
  border-radius: 20px;
  margin-right: 5px;
  font-size: 12px;
  color: #28303f;
}

.task-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.avatars {
  display: flex;
  align-items: center;
}

.avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 5px;
}

.due-date {
  font-size: 10px;
}

.more {
  position: absolute;
  left: 60px;
  font-size: 12px;
  color: #28303f;
}
</style>
