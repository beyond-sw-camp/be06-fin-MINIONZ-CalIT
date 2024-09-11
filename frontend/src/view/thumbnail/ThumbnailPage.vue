<script setup>
import thumbnailContent from '@/assets/img/thumbnail-content.svg';
import '@/assets/img/thumbnail-bg.svg';
</script>

<template>
  <div class="thumb-bg">
    <img :src="thumbnailContent" alt="">
  </div>
  <div class="btn-wrap">
    <router-link to="/user/login" class="btn-login">Login</router-link>
    <router-link to="/user/signup" class="btn-signup">Sign up</router-link>
  </div>
</template>

<style scoped>
.thumb-bg{
  background-image: url(@/assets/img/thumbnail-bg.svg);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}
.thumb-bg img{
  width: 90%;
  height: 90%;
}
.btn-wrap{
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  position: absolute;
  bottom: 10%;
  right: 15%;
}
a{
  box-shadow: 0 5px 10px rgba(89, 89, 132, 0.3);
  width: 100px;
  height: 55px;
  line-height: 55px;
  font-size: 16px;
  border-radius: 5px;
  text-align: center;
  text-decoration: none;
  transition: all 0.3s;
  &:hover{
    font-size: 18px;
  }
}
.btn-login{
  background-color: #595984;
  color: #fff;
  &:hover{
    background-color: #4a487f;
  }
}
.btn-signup{
  background-color: #f0f0f0;
  color: #595984;
  &:hover{
    background-color: #fff;
  }
}
</style>