<script setup>
import { ref } from 'vue';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";

const username = ref('');
const password = ref('');

const signup = () => {
  console.log('Signup', username.value, password.value);
};
</script>

<template>
  <div class="login-page">
    <div class="login-header">
      <h1>회원가입</h1>
      <div>
        <span>계정이 있으신가요?</span>
        <router-link to="/login">로그인 하기</router-link>
      </div>
    </div>
    <form @submit.prevent="signup">
      <UserInput input-placeholder="아이디를 입력하세요" label="아이디"/>
      <UserInput input-placeholder="이메일을 입력하세요" label="이메일"/>
      <UserInput input-placeholder="비밀번호를 입력하세요" label="비밀번호"/>
      <UserInput input-placeholder="비밀번호를 다시 입력하세요" label="비밀번호 확인"/>
      <UserButton type="submit" button-ment="Sign up"></UserButton>
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