<script setup>

import SideBar from "@/layouts/SideBar.vue";
import ContentsContainer from "@/layouts/ContentsContainer.vue";
import AppHeader from "@/layouts/AppHeader.vue";
import ContentsArea from "@/layouts/ContentsArea.vue";
</script>

<template>
  <div id="layout">
    <SideBar></SideBar>
    <ContentsContainer>
      <AppHeader></AppHeader>
      <router-view>
        <ContentsArea></ContentsArea>
      </router-view>
    </ContentsContainer>
  </div>
</template>

<style scoped>
#layout{
  display: flex;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
</style>