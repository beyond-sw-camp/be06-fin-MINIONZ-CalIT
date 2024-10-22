<script setup>
import { defineProps } from 'vue';
import { setPersona } from '@/utils/personaUtils';
import { formatDate } from '@/utils/timeUtils';

defineProps({
  task: {
    type: Object,
    required: true,
  },
});
</script>

<template>
  <div class="task-card">
    <div class="task-content">
      <div class="task-title">
        <div class="task-status-indicator"></div>
        <span>{{ task.title }}</span>
      </div>
    </div>
    <div class="list-items">
      <div class="labels">
        <span v-for="label in task.labels" :key="label" class="label">{{
          label.labelName
        }}</span>
      </div>
      <div class="list-item-bundle">
        <div class="task-meta">
          <div class="avatars">
            <img
              v-for="user in task.participants"
              :key="user.id"
              :src="setPersona(user.persona)"
              class="avatar"
              alt="users"
            />
          </div>
        </div>
        <div class="list-item-end">
          <span class="task-id">{{ task.taskNumber }}</span>
          <span class="due-date">{{ formatDate(task.endDate) }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.task-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background-color: #f7f8fa;
  border-radius: 8px;
  margin-bottom: 15px;
}

.task-content {
  display: flex;
}

.task-title {
  display: flex;
  align-items: center;
}

.task-status-indicator {
  width: 10px;
  height: 10px;
  background-color: #22c55e;
  border-radius: 50%;
  margin-right: 10px;
}

.labels {
  background-color: rgba(234, 179, 8, 0.1);
  color: #f87171;
  font-size: 12px;
  padding: 2px 8px;
  border-radius: 8px;
  margin-top: 5px;
}

.task-meta {
  display: flex;
  align-items: center;
}

.task-id {
  font-size: 14px;
  color: #28303F;
  background-color: #fff;
  padding: 3px 8px;
  border-radius: 15px;
  font-weight: 500;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.08);
}
.due-date {
  font-size: 12px;
  min-width: 80px;
  text-align: end;
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
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.08);
}

.list-items {
  display: flex;
  justify-content: space-between;
  width: 70%;
  align-items: center;
}

.list-item-bundle {
  width: 40%;
  display: flex;
  justify-content: space-between;
}

.list-item-end {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
