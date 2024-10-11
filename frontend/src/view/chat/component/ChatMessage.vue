<script setup>
import space6 from '@/assets/icon/persona/space6.svg';
import { defineProps } from 'vue';

defineProps({
  message:{
    type: Object,
    default: () => ({}),
  },
  isOwnMessage: {
    type: Boolean,
    required: true,
  },
  messageContents: {
    type: String,
    default: '',
  },
  file: {
    type: Object,
    default: () => ({}),
  },
  createdAt: {
    type: String,
    required: true,
  },
});

</script>

<template>
  <div :class="['message-container', isOwnMessage ? 'own' : '']">
    <img v-if="!isOwnMessage" class="profile-pic" :src="space6" alt="profile"/>
    <div class="message-content">
      <div class="message-bubble">
        <img v-if="message.file && message.file.fileUrl && message.file.fileType.startsWith('image/')" :src="message.file.fileUrl" alt="file" style="max-width: 230px"/>
        <a v-else-if="message.file && message.file.fileUrl" :href="message.file.fileUrl" target="_blank">{{ message.file.fileName || '파일 보기' }}</a>

        <!-- 메시지가 있을 경우 메시지 표시 -->
        <p v-else>{{ messageContents || 'No message' }}</p>
      </div>
      <span class="timestamp">{{ createdAt || 'No time' }}</span>
    </div>
    <img v-if="isOwnMessage" class="profile-pic" :src="space6" alt="profile"/>
  </div>
</template>

<style scoped>
.message-container {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
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

.timestamp {
  font-size: 12px;
  color: #888;
  margin-top: 5px;
  align-self: flex-end;
}
</style>