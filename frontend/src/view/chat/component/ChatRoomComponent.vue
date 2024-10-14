<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useChatMessageStore } from '@/stores/chat/useChatMessageStore';
import Message from './ChatMessage.vue';
import space3 from '@/assets/icon/persona/space3.svg';
import clip from '@/assets/icon/chatIcon/clip.svg';
import send from '@/assets/icon/chatIcon/sendIcon.svg';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { useUserStore } from "@/stores/user/useUserStore";
import { useRoute } from "vue-router";
import { getTimeDifference } from "@/utils/timeUtils";

const userStore = useUserStore();
const userId = userStore.user.value.idx;
const userName = userStore.user.value.userName;

const newMessage = ref('');
const messages = ref([]);
const stompClient = ref(null);
const fileInput = ref(null);
const selectedFile = ref(null);

const chatMessageStore = useChatMessageStore();
const route = useRoute();
const chatroomId = route.params.chatroomId;

onMounted(async () => {
  messages.value = (await chatMessageStore.fetchChatMessages(chatroomId)).reverse();

  // WebSocket 연결 설정
  const socket = new SockJS('http://localhost:8080/chat');
  stompClient.value = Stomp.over(socket);

  // WebSocket 연결 후 구독 설정
  stompClient.value.connect({}, (frame) => {
    console.log('Connected: ' + frame);

    stompClient.value.subscribe(`/sub/room/${chatroomId}`, (messageOutput) => {
      console.log('Received message from subscription: ' + messageOutput.body);

      const receivedMessage = JSON.parse(messageOutput.body);
      if (receivedMessage) {
        messages.value.unshift(receivedMessage);
      }
    });
  }, (error) => {
    console.error('STOMP 연결 오류:', error);
  });
});

onBeforeUnmount(() => {
  if (stompClient.value) {
    stompClient.value.disconnect();
  }
});

const onFileSelected = async (event) => {
  const file = event.target.files[0];
  if (file) {
    selectedFile.value = file;

    // 파일 업로드
    const fileUrls = await chatMessageStore.sendFile({ files: selectedFile.value, chatRoomId: chatroomId });
    console.log('fileUrls 제발 나와봐 : ', fileUrls);

    if (fileUrls) {
      console.log('파일 업로드 성공: ', fileUrls);

      // 메시지에 파일 URL 추가
      const messagePayload = {
        chatRoomId: chatroomId,
        userId: userId,
        userName: userName,
        messageContents: '',
        files: fileUrls,
      };
      stompClient.value.send(`/pub/room/${chatroomId}/send`, {}, JSON.stringify(messagePayload));
      selectedFile.value = null; // 파일 초기화
    } else {
      console.error('파일 업로드 실패');
    }
  }
};

// 메시지 보내기 함수
const sendMessage = async () => {
  if (!stompClient.value || !stompClient.value.connected) {
    console.error('STOMP 클라이언트가 연결되어 있지 않습니다');
    return;
  }

  if (newMessage.value.trim() === '' && !selectedFile.value) {
    console.error('메시지나 파일이 비어 있습니다');
    return;
  }

  let messagePayload = {
    chatRoomId: chatroomId,
    userId: userId,
    userName: userName,
    messageContents: newMessage.value,
    files: [],
  };

  if (selectedFile.value) {
    // 파일이 있는 경우, 파일을 업로드 후 URL을 받아서 전송
    const fileUrls = await chatMessageStore.sendFile({ files: selectedFile.value, chatRoomId: chatroomId });
    if (fileUrls) {
      messagePayload.files = fileUrls; // 파일 URL 추가
      messagePayload.messageContents = ''; // 파일이 있을 때는 내용 비우기
    }
    selectedFile.value = null; // 파일 초기화
  }

  stompClient.value.send(`/pub/room/${chatroomId}/send`, {}, JSON.stringify(messagePayload));
  newMessage.value = ''; // 메시지 전송 후 입력 필드 초기화
};

// 파일 입력창을 열기 위한 함수
const triggerFileInput = () => {
  fileInput.value.click();
};

</script>

<template>
  <div class="chat-container">
    <div class="chat-header">
      <img :src="space3" alt="img">
      <p>{{ userName }}</p>
    </div>

    <div class="chat-messages">
      <div class="chat-msg-container">
        <Message
            v-for="(message, index) in messages.slice().reverse()"
            :key="index"
            :message="message"
            :isOwnMessage="message.senderId === userId || message.userId === userId"
            :created-at="getTimeDifference(message.createdAt)"
            :message-contents="message.messageContents"
            :file="message.file && message.file.length ? message.file : null"
        />
      </div>
    </div>

    <div class="chat-input">
      <div>
        <input ref="fileInput" type="file" @change="onFileSelected" style="display: none;"/>
        <img :src="clip" alt="clip" @click="triggerFileInput">
      </div>
      <input v-model="newMessage" type="text" placeholder="메시지를 입력하세요" @keyup.enter="sendMessage"/>
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
