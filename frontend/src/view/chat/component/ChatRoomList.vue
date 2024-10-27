<script setup>
import {onMounted, ref, computed, watch} from 'vue';
import { useRoute } from 'vue-router';
import { getTimeDifference } from '@/utils/timeUtils';
import FriendsModal from './FriendsModal.vue';
import { useChatRoomStore } from '@/stores/chat/useChatRoomStore';
import { setPersona } from '@/utils/personaUtils';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const chatRoomStore = useChatRoomStore();

const chatRoom = computed(() => chatRoomStore.chatRoom);

const totalUnreadMessages = computed(() => {
  return chatRoom.value.reduce(
    (total, room) => total + (room.unreadMessages || 0),
    0
  );
});

onMounted(async () => {
  await chatRoomStore.fetchChatRooms(workspaceId);
});

const showModal = ref(false);

const openModal = async () => {
  showModal.value = true;
};

const closeModal = async () => {
  showModal.value = false;
};

watch(

)
</script>

<template>
  <div class="message-list-container">
    <div class="message-header">
      <p>
        Messages
        <!-- unreadMessages 총합을 보여줌 -->
        <span class="badge">{{ totalUnreadMessages }}</span>
      </p>
      <button class="new-message-button" @click="openModal">+</button>
    </div>
    <div class="message-list" v-if="chatRoom && chatRoom.length">
      <router-link
        :to="`/workspace/${workspaceId}/chat/` + room.chatroomId"
        class="message-item"
        v-for="room in chatRoom"
        :key="room.id"
      >
        <img :src="setPersona(2)" alt="profile" class="profile-pic" />
        <div class="message-info">
          <div class="message-item-top">
            <span class="user-name">{{ room.chatRoomName }}</span>
          </div>
          <p class="message-text">{{ room.messageContents }}</p>
        </div>
        <div class="message-item-right">
          <span class="message-time">{{
            getTimeDifference(room.createdAt)
          }}</span>
          <span v-if="room.unreadMessages" class="unread-count">{{
            room.unreadMessages
          }}</span>
        </div>
      </router-link>
    </div>
    <FriendsModal v-if="showModal" @close="closeModal" />
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
  color: #28303f;
}

.message-list-container {
  position: relative;
  height: 100vh;
  overflow: scroll;
}

.message-list {
  padding: 0 20px;
  height: 100%;
  overflow: auto;
  background-color: #fff;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  position: sticky;
  top: 0;
  height: 60px;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  font-weight: 500;
  border-bottom: 1px solid #e0e0e0;

  span {
    background-color: #edf2f7;
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
