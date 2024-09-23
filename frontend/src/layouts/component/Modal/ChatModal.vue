<script setup>
import { chatRoomList } from "@/static/chatData";
import { getTimeDifference} from "@/utils/timeUtils";
// import  notification from "@/assets/icon/alarm/notification.svg";
import 'perfect-scrollbar/css/perfect-scrollbar.css';
import PerfectScrollbar from "perfect-scrollbar";
import {onMounted} from "vue";
import info from "@/assets/icon/alarm/info.svg";

onMounted(() => {
  const container = document.querySelector('.chat-modal');
  new PerfectScrollbar(container);
});
</script>

<template>
  <div class="chat-modal">
    <div>
      <p>Chat List</p>
    </div>
    <hr>
    <ul>
      <li v-for="chat in chatRoomList" :key="chat.id">
        <router-link :to="'/workspace/' + chat.workspaceId + '/dashboard'">
        <div class="notification-item">
          <img :src="chat.profilePic" alt="alam" class="notify-img">
          <div>
            <p class="chat-title">{{ chat.chatRoomName }}</p>
            <p class="chat-content">{{ chat.messageContents }}</p>
          </div>
        </div>
        <div class="message-item-right">
          <span class="unread-count">{{ chat.unreadMessages }}</span>
          <p class="chat-time">{{ getTimeDifference(chat.createdAt) }}</p>
        </div>
        </router-link>
      </li>
        <li v-if="chatRoomList.length === 0">
          <div class="notification-item">
            <img :src="info" alt="alam">
            <div >
              <p class="chat-title"> 안읽은 메시지가 없습니다.</p>
            </div>
          </div>
        </li>
    </ul>
  </div>
</template>

<style scoped>
.chat-modal {
    position: absolute;
    top: 50px;
    right: 150px;
    background-color: #F3F6FF;
    border-radius: 10px;
    padding: 15px;
   width: 200px;
  max-height: 300px;
  overflow-y: auto;
  overflow-x: hidden;
  }

p {
    font-weight: 500;
    margin: 0;
}

.chat-title{
  font-size: 14px;
  font-weight: 500;
}

.chat-content{
  font-size: 14px;
  font-weight: 400;
  margin-top: 5px;
  color: #6b7280;
  width: 90px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.chat-time{
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
    width: 24px;
    height: 24px;
  }
}

.notify-img{
  width: 36px;
  height: 36px;
}

.notification-item{
  display: flex;
  gap: 10px;
}

.message-item-right {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
}
.unread-count {
  background-color: #e74c3c;
  color: white;
  font-size: 12px;
  border-radius: 15px;
  text-align: center;
  padding: 3px 10px;
}
</style>