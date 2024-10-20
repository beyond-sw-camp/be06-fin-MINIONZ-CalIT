<script setup>
import { computed, defineProps } from 'vue';

const props = defineProps({
  title: String,
  status: String,
  subheading: String,
  descriptionList: Array,
  owner: String,
  taskName: String,
});

const statusClass = computed(() => {
  return props.status === 'In Progress' ? 'in-progress' : 'done';
});
</script>

<template>
  <div class="task-card">
    <div class="header">
      <p class="title">{{ title }}</p>
      <span class="status" :class="statusClass">{{ status }}</span>
    </div>
    <div class="description">
      <p class="sub-heading">{{ subheading }}</p>
      <ul>
        <li
          v-for="(item, index) in descriptionList"
          :key="index"
          class="description-content"
        >
          {{ item }}
        </li>
      </ul>
    </div>
    <div class="footer">
      <p><strong>담당자:</strong> {{ owner }}</p>
      <p><strong>관련 태스크:</strong> {{ taskName }}</p>
    </div>
  </div>
</template>

<style scoped>
.task-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  width: 100%;
  background-color: white;
  box-sizing: border-box;
  min-height: 400px;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-size: 24px;
  font-weight: 500;
  margin: 0;
}

.sub-heading {
  font-size: 16px;
  font-weight: 500;
  margin: 0;
}

.description-content {
  font-size: 14px;
  margin-top: 10px;
}

.status {
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.in-progress {
  background-color: rgba(236, 72, 153, 0.1);
  color: rgb(236, 72, 153);
}

.done {
  background-color: rgba(34, 197, 94, 0.1);
  color: rgb(34, 197, 94);
}

.description {
  margin-top: 15px;
  margin-bottom: 20px;
}

.description h4 {
  margin-bottom: 10px;
}

.description ul {
  list-style: none;
  padding: 0;
}

.footer {
  margin-top: auto;
  font-size: 14px;
}
</style>
