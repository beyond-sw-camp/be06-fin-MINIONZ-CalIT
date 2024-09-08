<script setup>
import user2 from '@/assets/icon/persona/user2.svg';
import user3 from '@/assets/icon/persona/user3.svg';
import plus from '@/assets/icon/menu/plus.svg';
import { useRouter } from 'vue-router';
import { defineProps } from 'vue';

const props = defineProps({
  closeModal: {
    type: Function,
    required: true
  }
});

const router = useRouter();

const handleLinkClick = (path) => {
  router.push(path);
  props.closeModal();
};
</script>

<template>
  <div class="workspace-list-container">
    <div>
      <p>Workspace List</p>
    </div>
    <hr>
    <ul>
      <li @click="handleLinkClick('/workspace/dashboard')">
        <router-link to="/workspace/dashboard">
          <img :src="user2" alt="workspace1">
          <p>MINIONZ</p>
        </router-link>
      </li>
      <li @click="handleLinkClick('/workspace/dashboard')">
        <router-link to="/workspace/dashboard">
          <img :src="user3" alt="workspace2">
          <p>SIMKIDS</p>
        </router-link>
      </li>
    </ul>
    <hr>
    <div @click="handleLinkClick('/scrum/workspace')">
      <router-link to="/scrum/workspace">
        <img :src="plus" class="plus" alt="plus-btn">
        <p>Add Workspace</p>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
.workspace-list-container{
  background-color: #F3F6FF;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  position: absolute;
  top: 50px;
  right: 10px;
  width: 180px;

}
p{
  font-weight: 500;
  margin: 0;
}
hr{
  border: 1px solid #dfe5f1;
  width: 100%;
}
ul{
  text-decoration: none;
  padding: 0;
  li{
    list-style: none;
    padding: 0.5rem;
    gap: 0.625rem;
    display: flex;
    align-items: center;
    &:hover {
      background-color: #C6D2FD;
      color: white;
      border-radius: 10px;
    }
  }
}
a{
  display: flex;
  text-decoration: none;
  color: #222;
  align-items: center;
  gap: 10px;
  img{
    width: 40px;
    height: 40px;
  }
}
.plus{
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #93AAFD;
}
</style>