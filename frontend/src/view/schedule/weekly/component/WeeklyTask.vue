<script setup>
import { ref } from 'vue';
import task from '@/assets/icon/schedule/task.svg';

const issues = ref([
  { title: '회원 가입 기능', details: '진행중', icon: task },
  { title: '로그 아웃 기능', details: '진행중', icon: task},
  { title: '회원 가입 기능', details: '진행중', icon: task }
]);
</script>

<template>
  <div class="imminent-issues">
    <p class="imminent-issues-title">Imminent Issues</p>
    <div v-for="(issue, index) in issues" :key="index" class="issue-item">
      <img :src="issue.icon" alt="icon" class="issue-icon" />
      <div class="issue-info">
        <div class="issue-title">{{ issue.title }}</div>
        <div class="issue-details">{{ issue.details }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.imminent-issues {
  background-color: #fff;
  border-radius: 10px;
  //padding: 20px;
  width: 100%;
}

.imminent-issues-title {
  font-weight: 500;
  font-size: 16px;
  margin-bottom: 10px;
}

.issue-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  padding: 10px;
  //background-color: rgba(243, 246, 255, 0.7);
  background-color: #f9f9f9;
  border-radius: 8px;
}

.issue-icon {
  width: 40px;
  height: 40px;
  margin-right: 10px;
}

.issue-info {
  display: flex;
  justify-content: space-between;
  width: 100%;
  flex-direction: column;
}

.issue-title {
  font-weight: 400;
  font-size: 14px;
}

.issue-details {
  font-size: 12px;
  color: #777;
}
</style>