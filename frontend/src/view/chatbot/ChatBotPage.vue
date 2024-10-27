<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import SockJS from 'sockjs-client';
import { Stomp } from '@stomp/stompjs';
import { useUserStore } from '@/stores/user/useUserStore';
import { useChatBotStore } from '@/stores/chatbot/useChatBotStore';
import ChatMessage from './ChatMessage.vue';

const userStore = useUserStore();
const chatBotStore = useChatBotStore();
const newMessage = ref('');
const messages = ref([]);
const stompClient = ref(null);

// 메시지 정렬 함수
const sortMessagesByCreatedAt = (messages) => {
  return messages.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
};

onMounted(async () => {
  // 메시지 내역 불러오기
  await chatBotStore.loadBotMessages(userStore.user.value.idx);

  const messageList = chatBotStore.botMessageList?.result || [];

  messageList.forEach((msg) => {
    // 사용자
    if (msg.question) {
      messages.value.push({
        messageContents: msg.question,
        createdAt: msg.userQuestionAt,
        userId: msg.userId,
        isFromChatBot: false,
      });
    }

    // 챗봇
    if (msg.response) {
      messages.value.push({
        messageContents: msg.response,
        createdAt: msg.botResponseAt,
        userId: msg.userId,
        isFromChatBot: true,
      });
    }
  });

  // 메시지를 시간순으로 정렬
  messages.value = sortMessagesByCreatedAt(messages.value);

  const socket = new SockJS('/api/chatbot');
  stompClient.value = Stomp.over(socket);

  stompClient.value.connect({}, (frame) => {
    console.log('Connected: ' + frame);

    stompClient.value.subscribe(
      `/subUser/${userStore.user.value.idx}`,
      (messageOutput) => {
        try {
          const receivedMessage = JSON.parse(messageOutput.body);

          // 사용자
          if (receivedMessage.userId === userStore.user.value.idx) {
            messages.value.push({
              messageContents: receivedMessage.messageContents,
              createdAt: receivedMessage.createdAt || new Date().toISOString(),
              userId: receivedMessage.userId,
              isOwnMessage: true,
              isFromChatBot: false,
            });
          } else {
            // 챗봇
            messages.value.push({
              messageContents: receivedMessage.messageContents,
              createdAt: receivedMessage.createdAt || new Date().toISOString(),
              userId: receivedMessage.userId,
              isOwnMessage: false,
              isFromChatBot: true,
            });
          }

          // 메시지를 시간순으로 정렬
          messages.value = sortMessagesByCreatedAt(messages.value);
        } catch (e) {
          console.error('JSON 파싱 오류:', e);
        }
        scrollToBottom();
      }
    );
  });
});

onBeforeUnmount(() => {
  if (stompClient.value) {
    stompClient.value.disconnect();
  }
});

// 메시지 전송 함수
const sendMessage = () => {
  if (!stompClient.value || !stompClient.value.connected) {
    console.error('STOMP 클라이언트가 연결되어 있지 않습니다');
    return;
  }

  const messagePayload = {
    userId: userStore.user.value.idx,
    messageContents: newMessage.value, // 사용자의 메시지를 messageContents에 넣음
    isFromChatBot: false,
    createdAt: new Date().toISOString(),
  };

  if (
    !messagePayload.messageContents ||
    messagePayload.messageContents.trim() === ''
  ) {
    console.error('메시지 내용이 비어 있습니다.');
    return;
  }

  stompClient.value.send(
    `/pubBot/bot/message`,
    {},
    JSON.stringify(messagePayload)
  );

  messages.value.push(messagePayload);

  newMessage.value = '';
  scrollToBottom();
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
</script>

<template>
  <div class="chat-container">
    <div class="chat-messages">
      <div v-if="messages.length" class="chat-msg-container">
        <div v-for="(message, index) in messages" :key="index" class="message">
          <ChatMessage
            :message="message"
            :isOwnMessage="message.userId === userStore.user.value.idx"
            :isFromChatBot="message.isFromChatBot"
          />
        </div>
      </div>
    </div>

    <div class="chat-input">
      <input
        v-model="newMessage"
        type="text"
        placeholder="메시지를 입력하세요"
        @keyup.enter="sendMessage"
      />
      <button @click="sendMessage">보내기</button>
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 60px);
  width: 100%;
}

.chat-messages {
  overflow-y: auto;
  padding: 10px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.chat-msg-container {
  display: flex;
  flex-direction: column;
}

.chat-input {
  display: flex;
  padding: 10px;
  background-color: #fff;
  border-top: 1px solid #ddd;
}

.chat-input input {
  flex-grow: 1;
  padding: 10px;
  border-radius: 20px;
  border: 1px solid #ddd;
}

.chat-input button {
  margin-left: 10px;
  padding: 10px 20px;
  background-color: #4a90e2;
  color: white;
  border: none;
  border-radius: 20px;
  cursor: pointer;
}
</style>
