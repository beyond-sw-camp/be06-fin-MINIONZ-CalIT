<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useUserStore } from "@/stores/user/useUserStore";
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";
import SocialLogin from "@/view/user/component/SocialLogin.vue";
import axios from "axios";
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const router = useRouter();
const loginId= ref('');
const password = ref('');
const userStore = useUserStore();
const notyf = new Notyf();

const authenticate = async (loginId, password) => {
  try {
    const response = await axios.post('/api/user/login', {
      loginId: loginId,
      password: password
    });
    console.log("여기다")
    console.log(response.headers.getAuthorization().split(' ')[1])

    return response.headers.getAuthorization().split(' ')[1];
  } catch (error) {
    console.error('Login failed', error);
    if (error.response && error.response.data) {
      notyf.error(error.response.data.message || '로그인 실패');
    } else {
      notyf.error('로그인 실패');
    }
  } // 여기서 중괄호를 닫아야 함
}; // authenticate 함수 종료

const login = async () => {
  if (loginId.value === '' || password.value === '') {
    notyf.error('아이디와 비밀번호 모두 입력해주세요.');
    return;
  }

  const token = await authenticate(loginId.value, password.value);
  if (token) {
    sessionStorage.setItem('authToken', token);
    userStore.setToken(token);
    notyf.success('로그인 성공');
    await router.push('/my/dashboard');
  } else {
    notyf.error('로그인 실패');
  }
};
</script>

<template>
  <div class="login-page">
    <div class="login-header">
      <h1>로그인</h1>
    </div>
    <form @submit.prevent="login">
      <UserInput v-model="loginId" input-placeholder="아이디를 입력하세요" label="아이디" type="text" />
      <UserInput v-model="password" input-placeholder="비밀번호를 입력하세요" label="비밀번호" type="password"/>
        <UserButton type="submit" button-ment="Login" class="btn"></UserButton>
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
.btn{
  //color: #1E5EFF;
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