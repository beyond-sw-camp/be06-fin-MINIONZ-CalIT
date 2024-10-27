<script setup>
import { ref } from 'vue';
import chatNoData from '@/assets/img/chatNoData.svg';
import FriendsModal from './FriendsModal.vue';

const showModal = ref(false);

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};
</script>

<template>
  <div class="chat-container">
    <div class="chat-messages">
      <div class="chat-msg-container">
        <img :src="chatNoData" class="nodata-svg" alt="no Data" />
        <div class="nodata-info">
          <p>채팅을 시작해보세요</p>
          <button @click="openModal" class="open-btn">채팅방 추가</button>
        </div>
      </div>
    </div>
    <FriendsModal v-show="showModal" @close="closeModal" />
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

.chat-messages {
  overflow-y: auto;
  padding: 10px;
  height: 100%;
  margin-top: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.chat-msg-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  p {
    font-weight: 500;
    font-size: 20px;
    color: #28303f;
  }
}

.nodata-svg {
  width: 200px;
  height: 200px;
  margin: 0 auto;
}

.nodata-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  bottom: -50px;
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

.open-btn {
  background-color: #c6d2fd;
  font-size: 24px;
  border: none;
  padding: 10px 20px;
  border-radius: 20px;
  cursor: pointer;
  color: #28303f;
  margin-top: 10px;
  font-weight: 500;
}
</style>
