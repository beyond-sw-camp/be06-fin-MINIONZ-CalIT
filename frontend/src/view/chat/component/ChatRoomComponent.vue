<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useChatMessageStore } from '@/stores/chat/useChatMessageStore';
import Message from './ChatMessage.vue';
import ObserverComponent from './ObserverComponent.vue'; // 옵저버 컴포넌트
import space3 from '@/assets/icon/persona/space3.svg';
import clip from '@/assets/icon/chatIcon/clip.svg';
import send from '@/assets/icon/chatIcon/sendIcon.svg';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { useUserStore } from '@/stores/user/useUserStore';
import { useRoute } from 'vue-router';
import { getTimeDifference } from '@/utils/timeUtils';

const userStore = useUserStore();
const userId = userStore.user.value?.idx;
const userName = userStore.user.value?.userName;

const newMessage = ref('');
const messages = ref([]);
const stompClient = ref(null);
const fileInput = ref(null);
const selectedFile = ref(null);
const isLoading = ref(false);
const page = ref(0);
const size = 20;

const chatMessageStore = useChatMessageStore();
const route = useRoute();
const chatroomId = route.params.chatroomId;

onMounted(async () => {
  const initialMessages = await chatMessageStore.fetchChatMessages(
    chatroomId,
    page.value,
    size
  );
  if (initialMessages && initialMessages.length) {
    messages.value = [...initialMessages.reverse()]; // 최신 메시지가 맨 위로 가도록 역순 정렬
    page.value += 1;
    scrollToBottom(); // 처음 로드 시 스크롤을 맨 아래로 이동
  }

  // WebSocket 연결 설정
  const socket = new SockJS('/api/chat');
  stompClient.value = Stomp.over(socket);

  stompClient.value.connect(
    {},
    (frame) => {
      console.log('Connected: ' + frame);

      stompClient.value.subscribe(
        `/sub/room/${chatroomId}`,
        (messageOutput) => {
          const receivedMessage = JSON.parse(messageOutput.body);
          if (receivedMessage) {
            messages.value.push(receivedMessage); // 새 메시지는 아래에 추가
            scrollToBottom(); // 새 메시지가 추가되면 스크롤을 맨 아래로 이동
          }
        }
      );
    },
    (error) => {
      console.error('STOMP 연결 오류:', error);
    }
  );
});

onBeforeUnmount(() => {
  if (stompClient.value) {
    stompClient.value.disconnect();
  }
});

// 무한 스크롤을 위한 추가 메시지 로드 함수
const loadMoreMessages = async () => {
  if (isLoading.value) return;
  isLoading.value = true;

  const chatContainer = document.querySelector('.chat-messages');
  const previousScrollHeight = chatContainer.scrollHeight; // 이전 스크롤 높이 저장

  const additionalMessages = await chatMessageStore.fetchChatMessages(
    chatroomId,
    page.value,
    size
  );
  if (additionalMessages && additionalMessages.length) {
    messages.value = [...additionalMessages.reverse(), ...messages.value]; // 이전 메시지를 역순으로 추가
    page.value += 1;

    // 새로운 메시지를 추가한 후 스크롤 위치 유지 (이전 스크롤 위치로)
    setTimeout(() => {
      chatContainer.scrollTop =
        chatContainer.scrollHeight - previousScrollHeight;
    }, 0);
  }
  isLoading.value = false;
};

// 스크롤을 맨 아래로 이동시키는 함수
const scrollToBottom = () => {
  const chatContainer = document.querySelector('.chat-messages');
  if (chatContainer) {
    setTimeout(() => {
      chatContainer.scrollTop = chatContainer.scrollHeight;
    }, 0);
  }
};

const onFileSelected = async (event) => {
  const file = event.target.files[0];
  if (file) {
    selectedFile.value = file;

    await chatMessageStore.sendFile({
      files: selectedFile.value,
      chatRoomId: chatroomId,
    });
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
    const fileUrls = await chatMessageStore.sendFile({
      files: selectedFile.value,
      chatRoomId: chatroomId,
    });
    if (fileUrls) {
      messagePayload.files = fileUrls;
      messagePayload.messageContents = '';
    }
    selectedFile.value = null;
  }

  stompClient.value.send(
    `/pub/room/${chatroomId}/send`,
    {},
    JSON.stringify(messagePayload)
  );
  newMessage.value = '';
  scrollToBottom(); // 메시지를 보낼 때 스크롤을 맨 아래로 이동
};

// 파일 입력창을 열기
const triggerFileInput = () => {
  fileInput.value.click();
};
</script>

<template>
  <div class="chat-container">
    <div class="chat-header">
      <img :src="space3" alt="img" />
      <p>{{ userName }}</p>
    </div>

    <div class="chat-messages">
      <ObserverComponent :callback="loadMoreMessages" :isLoading="isLoading" />
      <div class="chat-msg-container">
        <Message
          v-for="(message, index) in messages"
          :key="index"
          :message="message"
          :isOwnMessage="
            message.senderId === userId || message.userId === userId
          "
          :created-at="getTimeDifference(message.createdAt)"
          :message-contents="message.messageContents"
          :file="message.file && message.file.length ? message.file : null"
        />
      </div>
    </div>

    <div class="chat-input">
      <div>
        <input
          ref="fileInput"
          type="file"
          @change="onFileSelected"
          style="display: none"
        />
        <img :src="clip" alt="clip" @click="triggerFileInput" />
      </div>
      <input
        v-model="newMessage"
        type="text"
        placeholder="메시지를 입력하세요"
        @keyup.enter="sendMessage"
      />
      <button @click="sendMessage">
        <img :src="send" alt="send" />
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
/* 여길 변경하니까 인식 잘 되는데 .reverse가 왜 있었는지 알아내야 한다.*/
.chat-messages {
  overflow-y: auto;
  padding: 10px;
  height: 100%;
  flex-direction: column;
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
