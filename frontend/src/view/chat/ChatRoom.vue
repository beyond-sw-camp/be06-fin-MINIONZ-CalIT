<script setup>
import ChatComponent from "@/view/chat/component/ChatComponent.vue";
import ChatRoomList from "@/view/chat/component/ChatRoomList.vue";
import FileList from "@/view/chat/component/FileList.vue";

// import { inject } from "vue";
//
// const contentsTitle = inject('contentsTitle');
// const contentsDescription = inject('contentsDescription');
//
// contentsTitle.value = 'Chatting Room';
// contentsDescription.value = '채팅방을 즐겨보세요!';
</script>

<template>
  <div class="chat-room">
    <ChatComponent />
    <div class="chat-sidebar">
      <ChatRoomList/>
      <FileList/>
    </div>
  </div>
</template>

<style scoped>
.chat-room{
  display: flex;
}
.chat-sidebar {
  display: flex;
  flex-direction: column;
  width: 370px;
  height: 100vh;
  border-left: 1px solid #e0e0e0;
}
</style>