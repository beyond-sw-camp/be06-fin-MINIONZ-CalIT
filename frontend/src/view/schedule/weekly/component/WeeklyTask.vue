<template>
  <div class="imminent-issues">
    <h3>Imminent Issues</h3>
    <div v-for="(issue, index) in issues" :key="index" class="issue-item">
      <img :src="issue.icon" alt="icon" class="issue-icon" />
      <div class="issue-info">
        <div class="issue-title">{{ issue.title }}</div>
        <div class="issue-details">{{ issue.details }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const issues = ref([
  { title: '회원 가입 기능', details: '중요도 마감일', icon: 'https://via.placeholder.com/30' },
  { title: '로그 아웃 기능', details: 'User 10:00 am', icon: 'https://via.placeholder.com/30' },
  { title: '회원 가입 기능', details: '중요도 마감일', icon: 'https://via.placeholder.com/30' }
]);
</script>

<style scoped>
.imminent-issues {
  background-color: #fff;
  border-radius: 10px;
  padding: 20px;
  width: 100%;
}

.issue-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.issue-icon {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.issue-info {
  display: flex;
  flex-direction: column;
}

.issue-title {
  font-weight: bold;
  font-size: 14px;
}

.issue-details {
  font-size: 12px;
  color: #777;
}
</style>