<script setup>
import { getTimeDifference } from "@/utils/timeUtils";
import  notification from "@/assets/icon/alarm/notification.svg";
import info from "@/assets/icon/alarm/info.svg";
import 'perfect-scrollbar/css/perfect-scrollbar.css';
import PerfectScrollbar from "perfect-scrollbar";
import {onMounted} from "vue";
import {useUserStore} from "@/stores/user/useUserStore";
import {useAlarmStore} from "@/stores/socket/useAlarmStore";
import { useWorkspaceStore } from "@/stores/workspace/space/useWorkspaceStore";

const alarmStore = useAlarmStore();
const workspaceStore = useWorkspaceStore();

onMounted(() => {
  const container = document.querySelector('.alarm-modal');
  new PerfectScrollbar(container);
});

const userStore = useUserStore();
const userId = userStore.user?.id;

const eventSource = new EventSource(`/api/alarm/connect/${userId}`);
eventSource.onmessage = function (event) {
  document.getElementById("message").textContent = 'Received event: ' + event.data;
  console.log('테스트 클라이언트 이벤트:' + event.data);
};
</script>

<template>
  <div class="alarm-modal">
    <div>
      <p>Alarm List</p>
    </div>
    <hr>
    <ul>
      <li v-for="alarm in alarmStore.alarms" :key="alarm.id">
        <div class="notification-item">
          <img :src="notification" alt="alam">
          <div >
            <p class="alarm-title">{{ alarm.title }}</p>
            <p class="alarm-content">{{ alarm.content }}</p>
          </div>
        </div>
          <div class="right-side accept-bundle" v-if="alarm.type === 1">
            <button @click="workspaceStore.acceptWorkspace">
              수락
            </button>
            <button @click="workspaceStore.rejectWorkspace">
              거절
            </button>
          <p class="alarm-time">{{ getTimeDifference(alarm.time) }}</p>
        </div>
        <div class="right-side normal-alarm" v-else>
          <button @click="alarmStore.deleteAlarm">
            <i class="delete-icon"></i>
          </button>
        </div>
      </li>
      <li v-if="alarmStore.alarms.length === 0">
        <div class="notification-item">
          <img :src="info" alt="alam">
          <div >
            <p class="alarm-title">알림이 없습니다.</p>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.alarm-modal {
    position: absolute;
    top: 50px;
    right: 100px;
    background-color: #F3F6FF;
    border-radius: 10px;
    padding: 15px;
  width: 300px;
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
  font-size: 12px;
  font-weight: 400;
  //margin-top: 5px;
  color: #6b7280;
  width: 170px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  text-wrap: inherit;
}

.alarm-time{
  font-size: 12px;
  font-weight: 400;
  margin-top: 5px;
  color: #6b7280;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

ul {
  text-decoration: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;

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

.right-side{
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

  .delete-icon{
    background-image: url('@/assets/icon/etc/cancel.svg');
    background-size: cover;
    width: 16px;
    height: 16px;
    display: block;
    color: #6b7280;
  }
</style>