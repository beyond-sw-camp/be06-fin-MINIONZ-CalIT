<script setup>
import { ref, watch } from 'vue';
import {useRoute, useRouter} from 'vue-router';

const route = useRoute();
const router = useRouter();
const showAvatarGroup = ref(true);

watch(
    () => route.path,
    (newPath) => {
      showAvatarGroup.value = !newPath.startsWith('/my');
    },
    { immediate: true}
)

import user1 from '@/assets/icon/persona/user1.svg';
import user2 from '@/assets/icon/persona/user2.svg';
import user3 from '@/assets/icon/persona/user3.svg';
import user4 from '@/assets/icon/persona/user4.svg';

const currentView = ref('board');
const avatars = ref([
  { src: user1, alt: 'User 1' },
  { src: user2, alt: 'User 2' },
  { src: user3, alt: 'User 3' },
  { src: user4, alt: 'User 4' }
]);

const setView = (view) => {
  currentView.value = view;
  const basePath = route.path.split('/').slice(0, -1).join('/');
  router.push(`${basePath}/${view}`);
};

const to = ref('kanban');
</script>

<template>
  <div class="view-toggle-bar">
    <div class="view-toggle-buttons">
      <router-link
          :to="to.startsWith('my') ? `/my/task/${currentView}` : to.startsWith('workspace') ? `/workspace/scrum/task/${currentView}` : to"
          :class="{ active: currentView === 'kanban' }"
          @click="setView('kanban')"
      >
        <i class="icon-kanban"></i>
        Kanban
      </router-link>
      <div class="v-line"/>
      <router-link
          :to="to.startsWith('my') ? `/my/task/${currentView}` : to.startsWith('workspace') ? `/workspace/task/${currentView}` : to"
          :class="{ active: currentView === 'list' }" @click="setView('list')">
        <i class="icon-list"></i>
        List
      </router-link>
      <div class="v-line"/>
      <router-link
          :to="to.startsWith('my') ? `/my/task/${currentView}` : to.startsWith('workspace') ? `/workspace/task/${currentView}` : to"
          :class="{ active: currentView === 'timeline' }" @click="setView('timeline')">
        <i class="icon-timeline"></i>
        TimeLine
      </router-link>
    </div>

    <div v-if="showAvatarGroup" class="avatar-group">
      <img v-for="(avatar, index) in avatars" :key="index" :src="avatar.src" :alt="avatar.alt" class="avatar" />
      <button class="add-avatar-btn">+</button>
    </div>
  </div>
</template>

<style scoped>
i{
  width: 24px;
  display: block;
  height: 24px;
}
a{
  display: flex;
  text-decoration: none;
  //padding: 10px;
  color: #28303F;
  border-radius: 4px;
  margin: 5px 10px;
  padding: 5px;
  &:hover, &:focus{
    background-color: #C6D2FD;
  }
}
.icon-kanban{
  background-image: url("@/assets/icon/menu/kanban.svg");
}
.icon-list{
  background-image: url("@/assets/icon/menu/list.svg");
}
.icon-timeline{
  background-image: url("@/assets/icon/menu/timeline.svg");
}
.view-toggle-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //padding: 10px;
  margin: 10px 30px;
}

.view-toggle-buttons {
  display: flex;
  align-items: center;
  //border: 1px solid #cccccc;
  background-color: #F3F6FF;
  border-radius: 4px;
  margin-top: 10px;
}

.view-toggle-buttons button {
  background: none;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 10px 20px;
  margin-right: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.view-toggle-buttons button.active {
  background-color: #e0f7fa;
  border-color: #00bcd4;
}

.view-toggle-buttons i {
  margin-right: 8px;
}

.avatar-group {
  display: flex;
  align-items: center;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-left: -10px;
  border: 2px solid white;
}

.more-avatars {
  background-color: #f0f0f0;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: -10px;
  font-size: 14px;
}

.add-avatar-btn {
  background-color: transparent;
  border: 2px solid #e0e0e0;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 10px;
  cursor: pointer;
  color: #00bcd4;
  font-size: 24px;
}

.v-line {
  border-left : 1px solid #93AAFD;
  height : 24px;
  margin: 0 10px;
}
</style>