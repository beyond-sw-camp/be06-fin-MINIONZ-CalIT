<script setup>
import { ref } from 'vue';
import message from '@/assets/icon/menu/message.svg';
import alarm from '@/assets/icon/menu/alarm.svg';
import user1 from '@/assets/icon/persona/user1.svg';
import arrow from '@/assets/icon/menu/arrow.svg';
import WorkspaceModal from "@/layouts/component/WorkspaceModal.vue";

const showModal = ref(false);

const toggleModal = () => {
  showModal.value = !showModal.value;
};

const closeModal = () => {
  showModal.value = false;
};
</script>

<template>
  <div class="header">
    <div>
      <p class="outfit">Seung Eun</p>
    </div>
    <div class="right-side">
      <div class="notice-bundle">
        <div class="message">
          <img :src="message" alt="message">
        </div>
        <div class="alarm">
          <img :src="alarm" alt="alarm">
        </div>
      </div>
      <div class="workspace-bundle" @click="toggleModal">
        <div>
          <img :src="user1" alt="persona" class="workspace-persona">
        </div>
        <div>
          <img :src="arrow" alt="arrow" :class="{ 'arrow-rotated': showModal }" class="arrow">
        </div>
      </div>
      <WorkspaceModal v-if="showModal" :close-modal="closeModal"/>
    </div>
  </div>
</template>

<style scoped>
.header {
  background-color: #fff;
  height: 60px;
  padding: 0 20px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  box-shadow: 5px 5px 5px 0 rgba(0, 0, 0, 0.15);
  position: fixed;
  top: 0;
  width: calc(100% - 16.25rem);
}
.right-side {
  display: flex;
  gap: 10px;
  align-items: center;
}
img {
  width: 30px;
  height: 30px;
}
.notice-bundle {
  display: flex;
  gap: 5px;
}
.workspace-persona {
  width: 40px;
  height: 40px;
}
.workspace-bundle {
  display: flex;
  align-items: center;
  cursor: pointer;
  .arrow {
    width: 30px;
    height: 30px;
  }
}

.arrow-rotated {
  transform: rotate(180deg);
  transition: transform 0.3s ease;
}
</style>