<script setup>
import dashboard from '@/assets/icon/menu/dashboard.svg';
import schedule from '@/assets/icon/menu/schedule.svg';
import task from '@/assets/icon/menu/task.svg';
</script>

<template>
  <ul>
    <li>
      <router-link to="/my/dashboard">
        <div class="menu-icon">
          <img :src="dashboard" alt="dashboard">
        </div>
        <p class="outfit">Dashboard</p>
      </router-link>
    </li>
    <li>
      <router-link to="/my/schedule/monthly">
        <img :src="schedule" alt="schedule">
        <p class="outfit">Schedule</p>
      </router-link>
    </li>
    <hr>
    <li>
      <router-link to="/my/task/kanban">
        <img :src="task" alt="task">
        <p class="outfit">Task</p>
      </router-link>
    </li>
  </ul>

</template>

<style scoped>
ul{
  list-style: none;
  padding: 0;
  margin: 0;
  a{
    padding: 0 1rem;
    gap: 0.625rem;
    text-decoration: none;
    display: flex;
    color: #000;
    font-weight: 300;
    height: 2.5rem;
    align-items: center;
    &:hover {
      background-color: #C6D2FD;
      color: white;
      border-radius: 10px;
      p{
        font-weight: 500;
      }
    }
  }
}
p{
  margin: 0;
  font-size: 1rem;
}
hr{
  border: 1px solid #dfe5f1;
  width: 100%;
}
</style>