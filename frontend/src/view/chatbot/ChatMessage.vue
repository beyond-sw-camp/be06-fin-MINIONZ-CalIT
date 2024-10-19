<script setup>
import space6 from '@/assets/icon/persona/chatbot.svg';
import { defineProps } from 'vue';
import { getTimeDifference } from '@/utils/timeUtils';

defineProps({
  message: {
    type: Object,
    default: () => ({}),
  },
  isOwnMessage: {
    type: Boolean,
    required: true,
  },
  isFromChatBot: {
    type: Boolean,
    required: true,
  },
});
</script>

<template>
  <div
    :class="[
      'message-container',
      isFromChatBot ? 'chatbot' : isOwnMessage ? 'own' : '',
    ]"
  >
    <img v-if="isFromChatBot" class="profile-pic" :src="space6" alt="profile" />

    <div class="message-content">
      <div class="message-bubble">
        <p
          v-html="
            message.messageContents
              ? message.messageContents.replace(/\n/g, '<br>')
              : 'No message'
          "
        ></p>
      </div>
      <span class="timestamp">{{
        getTimeDifference(message.createdAt) || 'No time'
      }}</span>
    </div>
  </div>
</template>

<style scoped>
.message-container {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
}

.chatbot {
  flex-direction: row;
}

.own {
  flex-direction: row-reverse;
}

.profile-pic {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin: 0 10px;
}

.message-content {
  display: flex;
  flex-direction: column;
}

.message-bubble {
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 20px;
  max-width: 250px;
}

.own .message-bubble {
  background-color: #4a90e2;
  color: white;
}

.chatbot .message-bubble {
  background-color: #f1f1f1;
}

.timestamp {
  font-size: 12px;
  color: #888;
  margin-top: 5px;
  align-self: flex-end;
}
</style>
