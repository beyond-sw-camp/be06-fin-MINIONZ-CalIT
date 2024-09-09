<script setup>
import itemIcon1 from '@/assets/icon/dashboard/itemIcon1.svg';
import itemIcon2 from '@/assets/icon/dashboard/itemIcon2.svg';
import itemIcon3 from '@/assets/icon/dashboard/itemIcon3.svg';

const tasksCompleted = 15;
const totalTasks = 20;
const completionRate = 95;
const workSpaceCount = 5;
</script>

<template>
  <div class="task-overview">
    <div class="overview-item">
      <div class="item-title">
        <img :src="itemIcon1" alt="tasks">
        <p>Task to complete</p>
      </div>
      <div class="item-contents">
        <div class="item-nums">
          <p class="highlight">{{ tasksCompleted }}</p>
          <p>/{{ totalTasks }}</p>
        </div>

        <a href="#">See all</a>
      </div>
    </div>
    <div class="overview-item">
      <div class="item-title">
        <img :src="itemIcon2" alt="rates">
        <p>Completion rate</p>
      </div>
      <div class="item-contents">
        <p class="highlight">{{ completionRate }}%</p>
        <a href="#">See all</a>
      </div>
    </div>
    <div class="overview-item">
      <div class="item-title">
        <img :src="itemIcon3" alt="Issues">
        <p>My Issues</p>
      </div>
      <div class="item-contents">
        <p class="highlight">{{ workSpaceCount }}</p>
        <a href="#">See all</a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.task-overview {
  display: flex;
  gap: 30px;
}

.overview-item {
  text-align: center;
  border: 1px solid #E3E3E3;
  border-radius: 16px;
  padding: 1rem;
  width: 220px;
  height: 110px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.item-nums{
  display: flex;
  gap: 5px;
  align-items: flex-end;
}

.highlight{
  font-size: 2rem;
  font-weight: 600;
}

.item-title {
  display: flex;
  align-items: center;
  gap: 10px;
}

h2 {
  font-size: 32px;
  margin: 0;
}

p{
  margin: 0;
}

.item-contents{
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  a{
    color: #909090;
    font-size: 14px;
    text-decoration: none;
  }
}
</style>