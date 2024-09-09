<script setup>
import { ref, computed } from 'vue';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";
import SocialLogin from "@/view/user/component/SocialLogin.vue";

const username = ref('');
const id = ref('');
const password = ref('');
const confirmPassword = ref('');
const email = ref('');
const showVerificationCode = ref(false);
const verificationCode = ref('');

const signup = () => {
  if (passwordsMatch.value) {
    console.log('Signup', username.value, password.value);
  } else {
    console.log('Passwords do not match');
  }
};

const showVerificationInput = () => {
  showVerificationCode.value = true;
};

const isEmailEntered = computed(() => {
  return email.value.trim() !== '';
});

const isVerificationCodeEntered = computed(() => {
  return verificationCode.value.trim() !== '';
});

const passwordsMatch = computed(() => {
  return password.value === confirmPassword.value;
});
</script>

<template>
  <div class="signup-page">
    <div class="signup-header">
      <h1>회원가입</h1>
      <div class="text-wrap">
        <span>계정이 있으신가요?</span>
        <router-link to="/user/login">로그인 하기</router-link>
      </div>
    </div>
    <div class="signup-body">
      <form @submit.prevent="signup">
        <UserInput v-model="username" input-placeholder="이름을 입력하세요" label="이름" type="text"/>
        <UserInput v-model="id" input-placeholder="아이디를 입력하세요" label="아이디" type="text"/>
        <div class="verify">
          <UserInput v-model="email" input-placeholder="이메일을 입력하세요" label="이메일" type="email"/>
          <button type="button" @click="showVerificationInput" class="btn-verify" :disabled="!isEmailEntered">인증 하기</button>
        </div>
        <div v-if="showVerificationCode" class="verify">
          <UserInput v-model="verificationCode" input-placeholder="인증 번호를 입력하세요" label="인증 번호" type="text"/>
          <button type="button" :disabled="!isVerificationCodeEntered" class="btn-verify">코드 입력</button>
        </div>
        <UserInput v-model="password" input-placeholder="비밀번호를 입력하세요" label="비밀번호" type="password"/>
        <UserInput v-model="confirmPassword" input-placeholder="비밀번호를 다시 입력하세요" label="비밀번호 확인" type="password"/>
        <p v-if="!passwordsMatch" class="error-message">비밀번호가 일치하지 않습니다.</p>
        <router-link to="/user/complete" style="margin: 0">
          <UserButton type="submit" button-ment="Sign up"></UserButton>
        </router-link>
      </form>
      <div class="text-wrap">
        <span>비밀번호가 기억나지 않으시나요?</span>
        <router-link to="/user/password">비밀번호 찾기</router-link>
      </div>
      <div class="social-login">
        <SocialLogin></SocialLogin>
      </div>
    </div>
  </div>
</template>

<style scoped>
.signup-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

h1 {
  margin: 0.625rem 0;
}

a {
  color: #1E5EFF;
  margin-left: 0.625rem;
  display: block;
}

.signup-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-bottom: 0.625rem;
}

.signup-body {
  width: 100%;
  overflow: scroll;
}

form {
  display: flex;
  flex-direction: column;
  gap: 0.9375rem;
  width: 100%;
}

.social-login {
  width: 100%;
}

.verify {
  display: flex;
  gap: 10px;
  align-items: flex-end;
}

.btn-verify {
  width: 6.25rem;
  height: 2.5rem;
  background-color: #1E5EFF;
  color: white;
  border: none;
  border-radius: 4px;

  &:focus {
    background-color: #1e52da;
  }
}

.verification-code {
  margin-top: 0.625rem;
}

.verification-code button:disabled {
  background-color: #d9e1ec;
  cursor: not-allowed;
}

.error-message {
  background-color: #fff1f1;
  padding: 0.625rem;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
  margin: 0;
}

.text-wrap{
  display: flex;
  justify-content: center;
  gap: 10px;
  width: 100%;
  span{
    text-align: end;
  }
}
</style>