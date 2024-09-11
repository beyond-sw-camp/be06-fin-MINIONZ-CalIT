<script setup>
import { ref, onMounted } from 'vue';
import { useChatMessageStore } from '@/store/socket/chat/useChatMessageStore';
import Message from './ChatMessage.vue';
// import user1 from '@/assets/icon/persona/user1.svg';
import space3 from '@/assets/icon/persona/space3.svg';
import clip from '@/assets/icon/chatIcon/clip.svg';
import send from '@/assets/icon/chatIcon/sendIcon.svg';

const chatPartner = "연희";
const newMessage = ref('');
const chatStore = useChatMessageStore();

// 채팅 메시지를 불러오는 메서드
const loadChatMessages = () => {
  chatStore.fetchMessages(chatStore.activeChatRoomId); // 현재 활성화된 채팅방 ID로 메시지를 조회
};

const sendMessage = () => {
  if (newMessage.value) {
    chatStore.sendMessage(chatStore.activeChatRoomId, newMessage.value);
    newMessage.value = '';
  }
};

const fileInput = ref(null);

const triggerFileInput = () => {
  fileInput.value.click();
};

// 컴포넌트가 마운트될 때 기존 채팅 메시지를 불러옴
onMounted(() => {
  loadChatMessages();  // 메시지 목록 조회
});
</script>

<template>
  <div class="chat-container">
    <div class="chat-header">
      <img :src="space3" alt="img">
      <p>{{ chatPartner }}</p>
    </div>

    <div class="chat-messages">
      <div class="chat-msg-container">
        <Message
            v-for="(message) in chatStore.messages"
            :key="message.id"
            :message="message.content"
            :isOwnMessage="message.isOwn"
        />
      </div>
    </div>

    <div class="chat-input">
      <div>
        <input ref="fileInput" type="file" style="display: none;" />
        <img :src="clip" alt="clip" @click="triggerFileInput">
      </div>
      <input v-model="newMessage" type="text" placeholder="Type a message" @keyup.enter="sendMessage" />
      <button @click="sendMessage">
        <img :src="send" alt="send">
      </button>
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 60px);
  width: 100%;
  position: relative;
}

.chat-header {
  background-color: #f5f6f9;
  padding: 10px;
  text-align: center;
  font-size: 20px;
  box-shadow: 0 3px 3px 0 rgba(0, 0, 0, 0.15);
  position: sticky;
  width: 100%;
  z-index: 10;
  display: flex;
  gap: 10px;
  align-items: center;
  box-sizing: border-box;
  img {
    width: 40px;
    height: 40px;
  }
  h2 {
    font-size: 20px;
  }
}

.chat-messages {
  overflow-y: auto;
  padding: 10px;
  height: 100%;
  //margin-top: 60px;
  flex-direction: column-reverse;
  display: flex;
}

.chat-msg-container{
  display: flex;
  flex-direction: column;
}

.chat-input {
  display: flex;
  border-top: 1px solid #ddd;
  padding: 10px;
  background-color: #fff;
  position: sticky;
  bottom: 0;
  width: 100%;
  box-sizing: border-box;
  height: 50px;
}

.chat-input input {
  flex-grow: 1;
  border: none;
  padding: 10px;
  border-radius: 20px;
  background-color: #f1f1f1;
}

.chat-input input:focus {
  outline: none;
}

.chat-input button {
  margin-left: 10px;
  background-color: #fff;
  border: none;
  cursor: pointer;
}

.chat-input button:disabled {
  background-color: #ccc;
  color: #909090;
}
</style>