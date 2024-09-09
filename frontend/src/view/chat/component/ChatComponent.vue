<script setup>
import { ref } from 'vue';
import Message from './ChatMessage.vue';
import user1 from '@/assets/icon/persona/user1.svg';
import space5 from '@/assets/icon/persona/space5.svg';
import clip from '@/assets/icon/chatIcon/clip.svg';
import send from '@/assets/icon/chatIcon/sendIcon.svg';

const chatPartner = "연희";

const messages = ref([
  { text: "연희의 채팅 기능", time: "10:07 AM", profilePic: space5, isOwn: false },
  { text: "응원해용 ✅", time: "10:08 AM", profilePic: space5, isOwn: false },
  { text: "예시 데이터", time: "10:08 AM", profilePic: space5, isOwn: false },
  { text: "연희야 화이팅", time: "10:08 AM", profilePic: user1, isOwn: true },
  { text: "슬이 화이팅", time: "10:08 AM", profilePic: user1, isOwn: true },
  { text: "성준 오빠 아쟈쟈", time: "10:08 AM", profilePic: user1, isOwn: true },
  { text: "내 짝꿍 혜정잉 😂", time: "10:08 AM", profilePic: user1, isOwn: true },
]);

const newMessage = ref('');

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    messages.value.push({
      text: newMessage.value,
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
      profilePic: user1,
      isOwn: true
    });
    newMessage.value = '';
  }
};

const fileInput = ref(null);

const triggerFileInput = () => {
  fileInput.value.click();
};
</script>

<template>
  <div class="chat-container">
    <div class="chat-header">
      <img :src="space5">
      <p>{{ chatPartner }}</p>
    </div>

    <div class="chat-messages">
      <div class="chat-msg-container">
        <Message
            v-for="(msg, index) in messages"
            :key="index"
            :message="msg"
            :isOwnMessage="msg.isOwn"
        />
      </div>

    </div>

    <div class="chat-input">
      <div>
        <input ref="fileInput" type="file" style="display: none;" />
        <img :src="clip" alt="clip" @click="triggerFileInput">
      </div>
      <input v-model="newMessage" type="text" placeholder="Type a message" @keyup.enter="sendMessage"/>
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
  height: 100vh;
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
  margin-bottom: 50px;
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