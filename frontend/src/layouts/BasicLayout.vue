<script setup>
import SideBar from "@/layouts/SideBar.vue";
import ContentsContainer from "@/layouts/ContentsContainer.vue";
import AppHeader from "@/layouts/AppHeader.vue";
import ContentsArea from "@/layouts/ContentsArea.vue";
import { useRoute } from 'vue-router';
import { onMounted, ref } from 'vue';
import { workspaceStore } from '@/stores/workspace/useWorkspaceStore';

const route = useRoute();
const isPersonalMenu = ref(false);

onMounted(() => {
  const workspaceId = route.params.workspaceId;
  if (workspaceId) {
    workspaceStore.workspaceId = workspaceId;
    isPersonalMenu.value = route.path.startsWith('/my');
  } else {
    console.error('workspaceId is undefined in BasicLayout');
  }
});
</script>

<template>
  <div id="layout">
    <SideBar :isPersonalMenu="isPersonalMenu"></SideBar>
    <ContentsContainer>
      <AppHeader></AppHeader>
      <router-view>
        <ContentsArea></ContentsArea>
      </router-view>
    </ContentsContainer>
  </div>
</template>

<style scoped>
#layout {
  display: flex;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>