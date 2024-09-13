<script setup>
import { ref } from 'vue';
import message from '@/assets/icon/menu/message.svg';
import alarm from '@/assets/icon/menu/alarm.svg';
import user1 from '@/assets/icon/persona/user1.svg';
import arrow from '@/assets/icon/menu/arrow.svg';
import WorkspaceModal from "@/layouts/component/Modal/WorkspaceModal.vue";
import NotificationModal from "@/layouts/component/Modal/NotificationModal.vue";

const showWorkspaceModal = ref(false);
const showNotificationModal = ref(false);

const toggleWorkspaceModal = () => {
  showWorkspaceModal.value = !showWorkspaceModal.value;
};

const toggleNotificationModal = () => {
  showNotificationModal.value = !showNotificationModal.value;
};

const closeWorkspaceModal = () => {
  showWorkspaceModal.value = false;
};
const closeNotificationModal = () => {
  showNotificationModal.value = false;
};
</script>

<template>
  <div class="header">
    <div class="user-name">
      <p class="outfit">Seung Eun</p>
    </div>
    <div class="right-side">
      <div class="notice-bundle">
        <router-link to="/workspace/chat" class="message">
          <img :src="message" alt="message">
        </router-link>
        <div class="alarm" @click="toggleNotificationModal">
          <img :src="alarm" alt="alarm">
        </div>
      </div>
      <div class="workspace-bundle" @click="toggleWorkspaceModal">
        <div>
          <img :src="user1" alt="persona" class="workspace-persona">
        </div>
        <div>
          <img :src="arrow" alt="arrow" :class="{ 'arrow-rotated': showWorkspaceModal }" class="arrow">
        </div>
      </div>
      <NotificationModal v-if="showNotificationModal" :close-modal="closeNotificationModal"/>
      <WorkspaceModal v-if="showWorkspaceModal" :close-modal="closeWorkspaceModal"/>
    </div>
  </div>
</template>

<style scoped>
.header {
  background-color: #fff;
  height: 60px;
  padding: 0 20px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
  position: fixed;
  top: 0;
  width: calc(100% - 16.25rem);
  z-index: 100;
}

.user-name {
  display: flex;
  align-content: center;
  flex-wrap: wrap;
}

.right-side {
  display: flex;
  gap: 10px;
  align-items: center;
}

img {
  width: 30px;
  height: 30px;
}

.notice-bundle {
  display: flex;
  gap: 5px;
  position: relative;
}

.workspace-persona {
  width: 40px;
  height: 40px;
}

.workspace-bundle {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.arrow {
  width: 30px;
  height: 30px;
}

.arrow-rotated {
  transform: rotate(180deg);
  transition: transform 0.3s ease;
}

.message::after {
  content: '';
  position: absolute;
  left: 20px;
  top: 3px;
  width: 10px;
  height: 10px;
  background-color: #FF6B6B;
  border-radius: 50%;
  border: 2px solid white;
}

.alarm::after {
  content: '';
  position: absolute;
  top: 3px;
  right: -5px;
  width: 10px;
  height: 10px;
  background-color: #FF6B6B;
  border-radius: 50%;
  border: 2px solid white;
}
</style>