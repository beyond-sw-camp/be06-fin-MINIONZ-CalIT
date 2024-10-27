<script setup>
import { computed, ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from '@/stores/user/useUserStore';
import { useWorkspaceStore } from '@/stores/workspace/useWorkspaceStore';
import PersonalMenu from '@/layouts/component/menu/PersonalMenu.vue';
import WorkSpaceMenu from '@/layouts/component/menu/WorkSpaceMenu.vue';
import user1 from '@/assets/icon/persona/user1.svg';
import router from '@/router';
import { Notyf } from 'notyf';
import axios from 'axios';

const route = useRoute();
const workspaceStore = useWorkspaceStore();
const isPersonalMenu = computed(() => route.path.startsWith('/my'));
const notyf = new Notyf();
const workspaceName = ref('');

watchEffect(async () => {
  if (isPersonalMenu.value) {
    workspaceName.value = 'My Space';
  } else {
    try {
      workspaceName.value = workspaceStore.nowWorkspace.workspaceName;
    } catch (error) {
      workspaceName.value = 'Workspace';
    }
  }
});

const logout = async () => {
  try {
    useUserStore().logout();
    await axios.post(`/api/user/logout`, {}, { withCredentials: true });
    notyf.success('로그아웃이 완료되었습니다');
    router.push('/user/login');
  } catch (error) {
    console.error('Logout failed', error);
    notyf.error('로그아웃에 실패했습니다. 다시 시도해 주세요. ');
  }
};
</script>

<template>
  <div class="sidebar_bg">
    <div class="logo_area">
      <img src="@/assets/img/logo.svg" alt="logo" />
    </div>
    <div class="user-info">
      <img :src="user1" alt="persona" />
      <p class="ubuntu-medium">
        {{ workspaceName }}
      </p>
    </div>
    <div class="menu-wrap">
      <div>
        <div v-if="isPersonalMenu">
          <PersonalMenu></PersonalMenu>
        </div>
        <div v-else>
          <WorkSpaceMenu></WorkSpaceMenu>
        </div>
      </div>
      <div>
        <hr />
        <button class="logout-wrap" @click="logout">
          <i class="logout-ico"></i>
          Logout
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.sidebar_bg {
  background-color: #f3f6ff;
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

.logo_area {
  display: flex;
  width: 13.25rem;
  height: 3.75rem;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
}

.user-info {
  display: flex;
  gap: 10px;
  align-items: center;
  font-size: 1.3rem;
  padding: 0.5rem 0 0.5rem 1rem;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: inset 3px 3px 3px 0 rgba(0, 0, 0, 0.15);

  p {
    margin: 0;
  }

  img {
    width: 2.5rem;
    border-radius: 50%;
    box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
  }
}

a {
  padding: 0 1rem;
  gap: 0.625rem;
  text-decoration: none;
  display: flex;
  color: #000;
  font-weight: 400;
}

.menu-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

button {
  background-color: transparent;
  border: none;
}

.logout-wrap {
  display: flex;
  align-items: center;
  gap: 0.625rem;
}

.logout-ico {
  background: url(@/assets/icon/menu/logout.svg) no-repeat;
  width: 24px;
  height: 24px;
  background-size: cover;
  display: block;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}
</style>
