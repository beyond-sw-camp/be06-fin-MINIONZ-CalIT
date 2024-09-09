<script setup>
import dashboard from '@/assets/icon/menu/dashboard.svg';
import schedule from '@/assets/icon/menu/schedule.svg';
import board from '@/assets/icon/menu/board.svg';
import qa from '@/assets/icon/menu/qa.svg';
import errors from '@/assets/icon/menu/errors.svg';
import scrum from '@/assets/icon/menu/scrum.svg';
import sprint from '@/assets/icon/menu/sprint.svg';
import task from '@/assets/icon/menu/task.svg';
import meeting from '@/assets/icon/menu/meeting.svg';
</script>

<template>
  <ul>
    <li>
      <router-link to="/workspace/dashboard">
        <img :src="dashboard" alt="dashboard">
        <p class="outfit">Dashboard</p>
      </router-link>
    </li>
    <li>
      <router-link to="/workspace/schedule/monthly">
        <img :src="schedule" alt="schedule">
        <p class="outfit">Schedule</p>
      </router-link>
    </li>
    <li>
      <router-link to="/workspace/board/qa">
        <img :src="board" alt="dashboard">
        <p class="outfit">Board</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link to="/workspace/board/qa">
            <img :src="qa" alt="qa">
            <p class="outfit">QA Board</p>
          </router-link>
        </li>
        <li>
          <router-link to="/workspace/board/error">
            <img :src="errors" alt="errors">
            <p class="outfit">Errors</p>
          </router-link>
        </li>
      </ul>
    </li>
    <hr>
    <li>
      <router-link to="/workspace/scrum/task/kanban">
        <img :src="scrum" alt="dashboard">
        <p class="outfit">Scrum</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link to="/workspace/scrum/sprint">
            <div>
              <img :src="sprint" alt="sprint">
            </div>
            <p class="outfit">Sprint</p>
          </router-link>
        </li>
        <li>
          <router-link to="/workspace/scrum/kanban">
            <img :src="task" alt="task">
            <p class="outfit">Task</p>
          </router-link>
        </li>
        <li>
          <router-link to="/workspace/scrum/meeting">
            <img :src="meeting" alt="meeting">
            <p class="outfit">Meeting</p>
          </router-link>
        </li>
      </ul>
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
.submenu{
  padding-left: 1.25rem;
}
hr{
  border: 1px solid #dfe5f1;
  width: 100%;
}
</style>