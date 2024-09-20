<script setup>
import {computed} from "vue";
import {useRoute} from "vue-router";
import {useUserStore} from '@/stores/user/useUserStore';
import PersonalMenu from "@/layouts/component/PersonalMenu.vue";
import WorkSpaceMenu from "@/layouts/component/WorkSpaceMenu.vue";
import user1 from "@/assets/icon/persona/user1.svg";
import router from "@/router";

const userStore = useUserStore();
const route = useRoute();

const showPersonalMenu = computed(() => {
  return route.path.startsWith('/my');
});

  const logout = () => {
  userStore.logout();
  router.push('/user/login');
};
</script>

<template>
  <div class="sidebar_bg">
    <div class="logo_area">
      <img src="@/assets/img/logo.svg" alt="logo">
    </div>
    <div class="user-info">
      <img :src="user1" alt="persona">
      <p class="ubuntu-medium">My Space</p>
    </div>
    <div class="menu-wrap">
      <div>
        <div v-if="showPersonalMenu">
          <PersonalMenu></PersonalMenu>
        </div>
        <div v-else>
          <WorkSpaceMenu></WorkSpaceMenu>
        </div>
      </div>
      <div>
        <button class="logout-wrap" @click="logout">
          <i class="logout-ico"></i>
          Logout
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .sidebar_bg{
    background-color: #F3F6FF;
    width: 16.25rem;
    padding: 1.25rem;
    gap: 0.625rem;
    display: flex;
    flex-direction: column;
    height: 100vh;
    box-sizing: border-box;
    position: fixed;
    z-index: 100;
  }
  .logo_area{
    display: flex;
    width: 13.25rem;
    height: 3.75rem;
    align-items: center;
    justify-content: center;
    //background-color: #fff;
    border-radius: 12px;
    //box-shadow: inset 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
  }
  .user-info{
    display: flex;
    gap: 10px;
    align-items: center;
    //justify-content: center;
    font-size: 1.3rem;
    padding: 0.5rem 0 0.5rem 1rem;
    background-color: #fff;
    //background: linear-gradient(45deg, #fff, #FEE6ED);
    border-radius: 12px;
    //border: 2px solid #dfe5f1;
    box-shadow: inset 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
    p{
      margin: 0;
    }
    img{
      width: 2.5rem;
      border-radius: 50%;
      box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
    }
  }
  a{
    padding: 0 1rem;
    gap: 0.625rem;
    text-decoration: none;
    display: flex;
    color: #000;
    font-weight: 400;
  }
  .menu-wrap{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
  }

  button{
    background-color: transparent;
    border: none;
  }

  .logout-wrap{
    display: flex;
    align-items: center;
    gap: 0.625rem;
  }

  .logout-ico{
    background: url(@/assets/icon/menu/logout.svg) no-repeat;
    width: 24px;
    height: 24px;
    background-size: cover;
    display: block;
  }
</style>