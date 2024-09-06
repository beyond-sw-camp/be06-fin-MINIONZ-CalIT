<script setup>

import PersonalMenu from "@/layouts/component/PersonalMenu.vue";
import WorkSpaceMenu from "@/layouts/component/WorkSpaceMenu.vue";

// if
</script>

<template>
  <div class="sidebar_bg">
    <div>
      <img src="@/assets/img/logo.svg" alt="logo">
      <div>
        <h2>User Name</h2>
      </div>
    </div>
    <PersonalMenu></PersonalMenu>
    <WorkSpaceMenu></WorkSpaceMenu>
  </div>
</template>

<style scoped>
  .sidebar_bg{
    background-color: #F5F6F9;
    width: 260px;
    //height: 100%;
    padding: 20px;
  }
</style>