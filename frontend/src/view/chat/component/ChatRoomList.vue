<script setup>
import user1 from '@/assets/icon/persona/user1.svg';
import user2 from '@/assets/icon/persona/user2.svg';
import user3 from '@/assets/icon/persona/user3.svg';
import user4 from '@/assets/icon/persona/user4.svg';
import user5 from '@/assets/icon/persona/user5.svg';



const messages = [
  { name: '최승은', text: '빠샤샤 🔥', time: '12m', unreadCount: 4, profilePic: user1},
  { name: '박성준', text: '메롱롱', time: '24m', profilePic: user2 },
  { name: '차윤슬', text: " 아쟈쟈", time: '1h', profilePic: user3 },
  { name: '지연희', text: '귀오밍', time: '5h', profilePic: user4 },
  { name: '강혜정', text: '오구오구', time: '2d', profilePic: user5 },
];
const totalMessages = 12;
</script>

<template>
  <div class="message-list-container">
    <div class="message-header">
      <p>Messages <span class="badge">{{ totalMessages }}</span></p>
      <button class="new-message-button">+</button>
    </div>
    <div class="message-list">
      <div class="message-item" v-for="(message, index) in messages" :key="index">
        <img :src="message.profilePic" alt="profile" class="profile-pic"/>
        <div class="message-info">
          <div class="message-item-top">
            <span class="user-name">{{ message.name }}</span>
          </div>
          <p class="message-text">{{ message.text }}</p>
        </div>
        <div class="message-item-right">
          <span class="message-time">{{ message.time }}</span>
          <span v-if="message.unreadCount" class="unread-count">{{ message.unreadCount }}</span>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
.message-list-container{
  position: relative;
  height: 50vh;
  overflow: scroll;
}

.message-list {
  padding: 0 20px;
  height: 100%;
  overflow: auto;
  //margin-top: 70px;
  background-color: #fff;
}

.message-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //margin-bottom: 10px;
  background-color: #FFF;
  position: sticky;
  top: 0;
  height: 60px;
  //box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  /* font-size: 20px; */
  font-weight: 500;
  border-bottom: 1px solid #e0e0e0;
  span{
    background-color: #EDF2F7;
    font-size: 12px;
    padding: 8px 12px ;
    border-radius: 24px;
    margin-left: 10px;
  }
}

.message-item-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //margin-bottom: 10px;
}

.new-message-button {
  background-color: #4a90e2;
  color: white;
  font-size: 24px;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.message-item {
  display: flex;
  align-items: center;
  /* padding: 10px 0; */
  border-bottom: 1px solid #f0f0f0;
  height: 72px;
}

.profile-pic {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}

.message-info {
  flex-grow: 1;
}

.user-name {
  font-weight: bold;
  font-size: 14px;
}

.message-item-right{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.message-time {
  color: #888;
  font-size: 12px;
}

.message-text {
  //margin: 5px 0 0;
  font-size: 12px;
}

.unread-count {
  background-color: #e74c3c;
  color: white;
  font-size: 12px;
  border-radius: 50%;
  padding: 5px 5px;
  text-align: center;
}
</style>