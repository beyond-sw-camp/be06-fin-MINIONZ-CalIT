<script setup>
import {onMounted, ref} from 'vue';
import {getTimeDifference} from '@/utils/timeUtils';
import FriendsModal from './FriendsModal.vue';
import { useChatRoomStore } from '@/stores/chat/useChatRoomStore';
import { useWorkspaceStore } from "@/stores/workspace/space/useWorkspaceStore";

const { chatRoom, fetchChatRooms } = useChatRoomStore();
onMounted(() => {
  fetchChatRooms();
});

const workspaceId = useWorkspaceStore().workspaceId;

const showModal = ref(false);

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};


// const totalMessages = 12;
</script>

<template>
  <div class="message-list-container">
    <div class="message-header">
      <p>Messages
        <span class="badge">{{ chatRoom.filter(room => room.unreadMessages > 0).length }}</span>
      </p>
      <button class="new-message-button" @click="openModal">+</button>
    </div>
    <div class="message-list">
      <router-link :to="`/workspace/${workspaceId}/chat/` + room.chatroomId" class="message-item" v-for="(room) in chatRoom" :key="room.id">
        <img :src="room.profilePic" alt="profile" class="profile-pic"/>
        <div class="message-info">
          <div class="message-item-top">
            <span class="user-name">{{ room.chatRoomName }}</span>
          </div>
          <p class="message-text">{{ room.messageContents }}</p>
        </div>
        <div class="message-item-right">
          <span class="message-time">{{ getTimeDifference(room.createdAt) }}</span>
          <span v-if="room.unreadMessages" class="unread-count">{{ room.unreadMessages }}</span>
        </div>
      </router-link>
    </div>
    <FriendsModal v-if="showModal" @close="closeModal"/>
  </div>
</template>

<!--<template>-->
<!--  <div class="message-list-container">-->
<!--    <div class="message-header">-->
<!--      <p>Messages <span class="badge">{{ totalMessages }}</span></p>-->
<!--      <button class="new-message-button" @click="openModal">+</button>-->
<!--    </div>-->
<!--    <div class="message-list">-->
<!--      <div class="message-item" v-for="(message, index) in chatRoomList" :key="index">-->
<!--        <img :src="user1" alt="profile" class="profile-pic"/>-->
<!--        <div class="message-info">-->
<!--          <div class="message-item-top">-->
<!--            <span class="user-name">{{ message.name }}</span>-->
<!--          </div>-->
<!--          <p class="message-text">{{ message.text }}</p>-->
<!--        </div>-->
<!--        <div class="message-item-right">-->
<!--          <span class="message-time">{{ message.time }}</span>-->
<!--          <span v-if="message.unreadCount" class="unread-count">{{ message.unreadCount }}</span>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--    <FriendsModal v-if="showModal" @close="closeModal" />-->
<!--  </div>-->
<!--</template>-->

<style scoped>
a{
  text-decoration: none;
  color: #28303F;
}

.message-list-container {
  position: relative;
  height: 50vh;
  overflow: scroll;
}

.message-list {
  padding: 0 20px;
  height: 100%;
  overflow: auto;
  //margin-top: 70px;
  background-color: #fff;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //margin-bottom: 10px;
  background-color: #FFF;
  position: sticky;
  top: 0;
  height: 60px;
  //box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  /* font-size: 20px; */
  font-weight: 500;
  border-bottom: 1px solid #e0e0e0;

  span {
    background-color: #EDF2F7;
    font-size: 12px;
    padding: 8px 12px;
    border-radius: 24px;
    margin-left: 10px;
  }
}

.message-item-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //margin-bottom: 10px;
}

.new-message-button {
  background-color: #4a90e2;
  color: white;
  font-size: 24px;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.message-item {
  display: flex;
  align-items: center;
  /* padding: 10px 0; */
  border-bottom: 1px solid #f0f0f0;
  height: 72px;
}

.profile-pic {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}

.message-info {
  flex-grow: 1;
}

.user-name {
  font-weight: 500;
  font-size: 14px;
}

.message-item-right {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
}

.message-time {
  color: #888;
  font-size: 12px;
}

.message-text {
  //margin: 5px 0 0;
  font-size: 12px;
  color: #595959;
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