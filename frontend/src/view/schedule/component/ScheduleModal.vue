<script setup>
import { defineEmits, defineProps } from 'vue';

const props = defineProps({
  title: String,
  startDate: String,
  endDate: String,
  contents: String,
  participants: String,
  top: Number,
  left: Number
});

const emit = defineEmits(['close']);
function handleClose() {
  emit('close');
}
</script>

<template>
  <div class="meeting-card" :style="{ top: `calc(${props.top}px + 30px)`, left: `calc(${props.left}px - 100px)` }">
    <div class="icon-bundle">
      <i class="edit-icon"></i>
      <i class="delete-icon"></i>
      <i class="close-icon" @click="handleClose"></i>
    </div>

    <div class="card-header">
      <div class="header">
        <p class="title">{{ props.title }}</p>
      </div>
      <div class="meeting-time">
        <i class="far fa-calendar-alt"></i>
        <span>{{ props.startDate }}</span>
        <span>~</span>
        <span>{{ props.endDate }}</span>
      </div>
    </div>

    <div class="content">
      <hr>
      <p>{{ props.contents }}</p>
      <hr>
    </div>

    <div class="participants">
      <p>참여자</p>
      <div class="personas">
        <p>{{ props.participants }}</p>
        <img src="@/assets/icon/persona/user1.svg" alt="User 1" class="persona" />
        <img src="@/assets/icon/persona/user2.svg" alt="User 2" class="persona" />
        <img src="@/assets/icon/persona/user3.svg" alt="User 3" class="persona" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.meeting-card {
  background-color: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 300px;
  font-family: 'Arial', sans-serif;
  position: absolute;
}

.icon-bundle {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  i {
    cursor: pointer;
    height: 16px;
    width: 16px;
    background-repeat: no-repeat;
    background-size: contain;
  }
  .edit-icon {
    background-image: url("@/assets/icon/modal/edit.svg");
  }
  .delete-icon {
    background-image: url("@/assets/icon/modal/delete.svg");
  }
  .close-icon {
    background-image: url("@/assets/icon/modal/close.svg");
  }
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.title {
  font-size: 18px;
  font-weight: bold;
  margin: 0;
}

.meeting-time {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #555;
  margin-bottom: 15px;
}

.meeting-time i {
  margin-right: 10px;
  color: #999;
}

.content {
  font-size: 14px;
  color: #333;
  margin-bottom: 20px;
}

.participants {
  font-size: 14px;
  color: #555;
}

.personas {
  display: flex;
  margin-top: 10px;
}

.persona {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 8px;
}
</style>