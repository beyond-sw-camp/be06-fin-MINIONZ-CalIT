<script setup>
import { ref } from 'vue';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";

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
      <div>
        <span>처음이신가요?</span>
        <router-link to="/signup">회원가입 하기</router-link>
      </div>
    </div>
    <form @submit.prevent="login">
      <UserInput input-placeholder="아이디를 입력하세요" label="아이디"/>
      <UserInput input-placeholder="비밀번호를 입력하세요" label="비밀번호"/>
      <UserButton type="submit" button-ment="Login"></UserButton>
    </form>
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
  margin: 10px 0;
}
a{
  color: #1E5EFF;
  margin-left: 10px;
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
</style>