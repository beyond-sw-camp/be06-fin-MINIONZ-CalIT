<script setup>
import {getTimeDifference} from "@/utils/timeUtils";
import notification from "@/assets/icon/alarm/notification.svg";
import info from "@/assets/icon/alarm/info.svg";
import 'perfect-scrollbar/css/perfect-scrollbar.css';
import PerfectScrollbar from "perfect-scrollbar";
import { onMounted } from "vue";
import { useAlarmStore } from "@/stores/alarm/useAlarmStore";
import { useWorkspaceStore } from "@/stores/workspace/useWorkspaceStore";

const alarmStore = useAlarmStore();
const workspaceStore = useWorkspaceStore();

onMounted(async () => {
  const container = document.querySelector('.alarm-modal');
  new PerfectScrollbar(container);
  await alarmStore.getAlarmData();
});

const handleReject = (alarm) => {
  workspaceStore.rejectWorkspace(alarm.idx);
  alarmStore.deleteAlarm(alarm.userAlarmId);
  alarmStore.getAlarmData();
};

const handleAccept = (alarm) => {
  workspaceStore.acceptWorkspace(alarm.idx);
  alarmStore.deleteAlarm(alarm.userAlarmId);
  alarmStore.getAlarmData();
};

const handleRead = (alarm) => {
  alarmStore.deleteAlarm(alarm.userAlarmId);
  alarmStore.getAlarmData();
}
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
          <div class="notification-info">
            <p class="alarm-title">{{ alarm.title }}</p>
            <p class="alarm-content">{{ alarm.content }}</p>
          </div>
        </div>
        <div class="right-side accept-bundle" v-if="alarm.type === 1">
          <div class="btn-bundle">
            <button @click="handleAccept(alarm)" class="btn-accept">
              수락
            </button>
            <button @click="handleReject(alarm)" class="btn-reject">
              거절
            </button>
          </div>
          <p class="alarm-time">{{ getTimeDifference(Date.parse(alarm.time)) }}</p>
        </div>
        <div class="right-side normal-alarm" v-else>
          <button @click="handleRead(alarm)">
            <i class="delete-icon"></i>
          </button>
        </div>
      </li>
      <li v-if="alarmStore.alarms.length === 0">
        <div class="notification-item">
          <img :src="info" alt="alam">
          <div>
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
  width: 350px;
}

p {
  font-weight: 500;
  margin: 0;
}

.alarm-title {
  font-size: 14px;
  font-weight: 500;
}

.alarm-content {
  font-size: 12px;
  font-weight: 400;
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

.alarm-time {
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

.notification-info {
  display: flex;
  flex-direction: column;
  gap: 5px;
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
      .btn-accept {
        background-color: #fff;
      }
      .btn-reject{
        background-color: #d9e1ec;
      }
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

.notification-item {
  display: flex;
  gap: 10px;
}

button {
  background-color: transparent;
  border: none;
}

.right-side {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.delete-icon {
  background-image: url('@/assets/icon/etc/cancel.svg');
  background-size: cover;
  width: 16px;
  height: 16px;
  display: block;
  color: #6b7280;
}

.btn-bundle {
  display: flex;
  justify-content: flex-end;
  font-size: 10px;
  width: 100%;

  .btn-accept {
    background-color: #C6D2FD;
    color: #28303F;
    padding: 5px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-right: 5px;
  }

  .btn-reject {
    border: #cccccc;
    padding: 5px;
    color: #28303F;
    border-radius: 5px;
    cursor: pointer;
  }
}
</style>