<script setup>
import {ref, defineProps, onMounted, onBeforeUnmount} from 'vue';
import {useChatMessageStore} from '@/stores/chat/useChatMessageStore';
import Message from './ChatMessage.vue';
import space3 from '@/assets/icon/persona/space3.svg';
import clip from '@/assets/icon/chatIcon/clip.svg';
import send from '@/assets/icon/chatIcon/sendIcon.svg';
import SockJS from 'sockjs-client';
import {Stomp} from '@stomp/stompjs';

const props = defineProps({
  chatroomId: Number,
  userId: Number,
  userName: String,
});

const newMessage = ref('');
const messages = ref([]);
const stompClient = ref(null);
const fileInput = ref(null);

const chatMessageStore = useChatMessageStore();

onMounted(async () => {
  // Fetch initial chat messages
  messages.value = await chatMessageStore.fetchChatMessages(props.chatroomId);

  // WebSocket 연결 설정
  const socket = new SockJS('http://localhost:8080/chat');
  stompClient.value = Stomp.over(socket);

  // WebSocket 연결 후 구독 설정
  stompClient.value.connect({}, (frame) => {
    console.log('Connected: ' + frame);

    stompClient.value.subscribe(`/sub/room/${props.chatroomId}`, (messageOutput) => {
      console.log('messageOutput: ' + messageOutput);

      const receivedMessage = JSON.parse(messageOutput.body);
      if (receivedMessage) {
        messages.value.push(receivedMessage);
        chatMessageStore.addMessage(receivedMessage); // Store에 메시지 추가
      }
    });
  }, (error) => {
    console.error('STOMP 연결 오류:', error);
  });
});

onBeforeUnmount(() => {
  // 컴포넌트가 사라지기 전에 WebSocket 연결 해제
  if (stompClient.value) {
    stompClient.value.disconnect();
  }
});

const sendMessage = async () => {
  if (stompClient.value && stompClient.value.connected && newMessage.value.trim() !== '') {
    const messagePayload = {
      chatRoomId: props.chatroomId,
      userId: props.userId,
      userName: props.userName,
      messageContents: newMessage.value,
    };
    stompClient.value.send(`/pub/room/${props.chatroomId}/send`, {}, JSON.stringify(messagePayload));
    newMessage.value = ''; // 메시지 전송 후 입력 필드 초기화
  } else {
    console.error('STOMP client is not connected or message is empty');
  }
};

const triggerFileInput = () => {
  fileInput.value.click();
};
</script>

<template>
  <div class="chat-container">
    <div class="chat-header">
      <img :src="space3" alt="img">
      <p>{{ props.userName }}</p>
    </div>

    <div class="chat-messages">
      <div class="chat-msg-container">
        <Message
            v-for="(message, index) in messages"
            :key="index"
            :message="message"
            :isOwnMessage="message.userId === props.userId"
        />
      </div>
    </div>

    <div class="chat-input">
      <div>
        <input ref="fileInput" type="file" style="display: none;"/>
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
  flex-direction: column-reverse;
  display: flex;
}

.chat-msg-container {
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