<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue';
import { useUserStore } from '@/stores/user/useUserStore';
import { useAlarmStore } from '@/stores/alarm/useAlarmStore';
import { useWorkspaceStore } from '@/stores/workspace/useWorkspaceStore';
import ChatModal from '@/layouts/component/modal/ChatModal.vue';
import AlarmModal from '@/layouts/component/modal/AlarmModal.vue';
import WorkspaceModal from '@/layouts/component/modal/WorkspaceModal.vue';
import chatbot from '@/assets/icon/menu/chatbot.svg';
import message from '@/assets/icon/menu/message.svg';
import alarm from '@/assets/icon/menu/alarm.svg';
import user1 from '@/assets/icon/persona/user1.svg';
import arrow from '@/assets/icon/menu/arrow.svg';

const showChatModal = ref(false);
const showAlarmModal = ref(false);
const showWorkspaceModal = ref(false);

const toggleChatModal = () => {
  showChatModal.value = !showChatModal.value;
};

const toggleAlarmModal = () => {
  showAlarmModal.value = !showAlarmModal.value;
};

const toggleWorkspaceModal = () => {
  showWorkspaceModal.value = !showWorkspaceModal.value;
};

const closeChatModal = () => {
  showChatModal.value = false;
};

const closeAlarmModal = () => {
  showAlarmModal.value = false;
};

const closeWorkspaceModal = () => {
  showWorkspaceModal.value = false;
};

const handleClickOutside = (event) => {
  if (showChatModal.value && !event.target.closest('.chat') && !event.target.closest('.notification-item') && !event.target.closest('.chat-modal')) {
    closeChatModal();
  }
  if (showAlarmModal.value && !event.target.closest('.alarm') && !event.target.closest('.notification-item') && !event.target.closest('.alarm-modal')) {
    closeAlarmModal();
  }
  if (showWorkspaceModal.value && !event.target.closest('.workspace-bundle') && !event.target.closest('.workspace-list-container')) {
    closeWorkspaceModal();
  }
};

onMounted(async () => {
  document.addEventListener('click', handleClickOutside);
  await alarmStore.getAlarmData();
});

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside);
});

const workspaceId = useWorkspaceStore().workspaceId;
const userStore = useUserStore();
const alarmStore = useAlarmStore();

const userId = userStore.user.value?.idx;

const loginId = computed(() => {
  return userStore.user.value ? userStore.user.value.loginId : '';
});
</script>

<template>
  <div class="header">
    <div class="user-name">
      <p class="outfit">{{ loginId }}</p>
    </div>
    <div class="right-side">
      <div class="notice-bundle">
        <div>
          <router-link :to="`/workspace/${workspaceId}/chatbot/${userId}`">
            <img :src="chatbot" alt="chatbot" style="width: 27px; height: 27px">
          </router-link>
        </div>
        <div class="chat" @click="toggleChatModal">
          <img :src="message" alt="chat">
        </div>
        <div class="alarm" @click="toggleAlarmModal">
          <img :src="alarm" alt="alarm">
          <span v-show="alarmStore.alarms.length > 0" class="alarm-indicator"></span>
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
      <ChatModal v-if="showChatModal" :close-modal="closeChatModal"/>
      <AlarmModal v-if="showAlarmModal" :close-modal="closeAlarmModal"/>
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
  gap: 20px;
  align-items: center;
}

img {
  width: 30px;
  height: 30px;
}

.notice-bundle {
  display: flex;
  gap: 10px;
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

.chat::after {
  content: '';
  position: absolute;
  right: 2.4rem;
  top: 3px;
  width: 10px;
  height: 10px;
  background-color: #FF6B6B;
  border-radius: 50%;
  border: 2px solid white;
}

.alarm-indicator {
  position: absolute;
  top: 3px;
  right: 0;
  width: 10px;
  height: 10px;
  background-color: #FF6B6B;
  border-radius: 50%;
  border: 2px solid white;
}
</style>