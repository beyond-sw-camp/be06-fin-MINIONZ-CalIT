<script setup>
import ChatRoomComponent from "@/view/chat/component/ChatRoomComponent.vue";
import ChatRoomList from "@/view/chat/component/ChatRoomList.vue";
import FileList from "@/view/chat/component/FileList.vue";
</script>

<template>
  <div class="chat-room">
    <ChatRoomComponent/>
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
  height: calc(100vh - 60px);
  border-left: 1px solid #e0e0e0;
}
</style>