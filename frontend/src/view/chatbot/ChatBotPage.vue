<script setup>
import ChatRoomComponent from "@/view/chat/component/ChatRoomComponent.vue";
</script>

<template>
  <div class="chat-room">
    <ChatRoomComponent/>
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
  height: calc(100vh - 60px);
  border-left: 1px solid #e0e0e0;
}
</style>