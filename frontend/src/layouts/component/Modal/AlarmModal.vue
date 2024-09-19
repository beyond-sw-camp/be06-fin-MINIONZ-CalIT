<script setup>
// import {alarmData} from "@/static/alarmData";
// import { useAlarmStore } from '@/stores/socket/useAlamStore';
import  notification from "@/assets/icon/alarm/notification.svg";
// const alarmStore = useAlarmStore();


</script>

<template>
  <div class="alarm-list-container">
    <div>
      <p>Alarm List</p>
    </div>
    <hr>
    <ul>
      <li v-for="alarm in alarmData" :key="alarm.id">
        <div class="notification-item">
          <img :src="notification" alt="alam">
          <div>
            <p class="alarm-title">{{ alarm.title }}</p>
            <p class="alarm-content">{{ alarm.content }}</p>
          </div>
        </div>
        <div>
          <button>
            <i class="delete-icon"></i>
          </button>
          <p class="alarm-time">{{ alarm.time}}</p>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.alarm-list-container {
    position: absolute;
    top: 50px;
    right: 100px;
    background-color: #F3F6FF;
    border-radius: 10px;
    padding: 10px;
  width: 200px;
  }

p {
    font-weight: 500;
    margin: 0;
}

.alarm-title{
  font-size: 14px;
  font-weight: 500;
}

.alarm-content{
  font-size: 14px;
  font-weight: 400;
  margin-top: 5px;
  color: #7c7c7c;
}

.alarm-time{
  font-size: 12px;
  font-weight: 400;
  margin-top: 5px;
  color: #7c7c7c;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

ul {
  text-decoration: none;
  padding: 0;

  li {
    list-style: none;
    padding: 0.5rem;
    gap: 0.625rem;
    display: flex;
    align-items: center;
    background-color: #fff;
    border-radius: 10px;
    justify-content: space-between;

    &:hover {
      background-color: #C6D2FD;
      color: white;
      border-radius: 10px;
    }
  }
}

a {
  display: flex;
  text-decoration: none;
  color: #222;
  align-items: center;
  gap: 10px;

  img {
    width: 40px;
    height: 40px;
  }
}

.notification-item{
  display: flex;
  gap: 10px;
}

button{
  background-color: transparent;
  border: none;
}

  .delete-icon{
    background-image: url('@/assets/icon/etc/cancel.svg');
    background-size: cover;
    width: 24px;
    height: 24px;
    display: block;
  }
</style>