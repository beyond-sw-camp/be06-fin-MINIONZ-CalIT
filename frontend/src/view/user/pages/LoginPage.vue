<script setup>
import { ref } from 'vue';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";
import SocialLogin from "@/view/user/component/SocialLogin.vue";



const username = ref('');
const password = ref('');

const login = () => {
  console.log('Login', username.value, password.value);
};
</script>

<template>
  <div class="login-page">
    <div class="login-header">
      <h1>로그인</h1>
    </div>
    <form @submit.prevent="login">
      <UserInput input-placeholder="아이디를 입력하세요" label="아이디" type="email"/>
      <UserInput input-placeholder="비밀번호를 입력하세요" label="비밀번호" type="password"/>
      <router-link to="/my/dashboard">
        <UserButton type="submit" button-ment="Login"></UserButton>
      </router-link>
    </form>
    <div class="link-wrap">
      <router-link to="/user/password" >비밀번호 찾기</router-link>
      <router-link to="/user/signup">회원가입 하기</router-link>
    </div>
    <div class="social-login">
      <SocialLogin></SocialLogin>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
h1{
  margin: 10px;
}
a{
  color: #1E5EFF;
  //margin-left: 10px;
  text-decoration: none;
}
.login-header{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-bottom: 10px;
}
form {
  display: flex;
  flex-direction: column;
  gap: 15px;
  width: 100%;
}
.social-login{
  width: 100%;
}
.link-wrap{
  display: flex;
  gap: 1rem;
}
</style>