<script setup>

import PersonalMenu from "@/layouts/component/PersonalMenu.vue";
import WorkSpaceMenu from "@/layouts/component/WorkSpaceMenu.vue";

// if
</script>

<template>
  <div class="sidebar_bg">
    <div class="logo_area">
      <img src="@/assets/img/logo.svg" alt="logo">
    </div>
    <hr>
    <div class="user-info">
      <h2>User Name</h2>
    </div>
    <PersonalMenu></PersonalMenu>
    <WorkSpaceMenu></WorkSpaceMenu>
  </div>
</template>

<style scoped>
  .sidebar_bg{
    background-color: #F5F6F9;
    width: 260px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  .logo_area{
    display: flex;
    width: 212px;
    height: 60px;
    align-items: center;
    justify-content: center;
    background-color: #fff;
    border-radius: 12px;
    box-shadow: inset 5px 5px 5px 0 rgba(0, 0, 0, 0.15);
  }
  hr{
    border: 1px solid #d1d3db;
    width: 100%;
  }
  .user-info{

  }
</style>