<script setup>
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";
import axios from "axios";
import {Notyf} from 'notyf';

const router = useRouter();
const notyf = new Notyf();

const loginId = ref('');

const signup = () => {
  if (checkId(loginId.value)) {
    try {
      axios.post('/api/user/signup', {
        loginId: loginId.value
      });
      notyf.success('회원가입에 성공했습니다.');
      return router.push('/my/dashboard');
    } catch (error) {
      console.error('Signup failed', error);
      notyf.error('회원가입에 실패했습니다.');
    }
  } else {
    notyf.error('빈 칸을 채워주세요!');
  }
}

const checkId = async (loginId) => {
  const r = await axios.post('/api/user/social-redirect', {
    loginId: loginId,
  });
  if (r.data.success) {
    notyf.success('사용 가능한 아이디 입니다.');
  } else {
    notyf.error("중복된 아이디 입니다.");
  }
}
</script>

<template>
  <div class="signup-page">
    <div class="signup-header">
      <h1>회원 정보 작성</h1>
    </div>
    <div class="signup-body">
      <form @submit.prevent="signup">
        <div class="verify">
          <UserInput v-model="loginId" input-placeholder="아이디를 입력하세요" label="아이디" type="text"/>
          <button type="button" @click="checkId(loginId.value)" class="btn-verify">중복 확인</button>
        </div>

        <div @click="signup" style="margin: 0">
          <UserButton type="submit" button-ment="Sign up"></UserButton>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
button {
  cursor: pointer;
}

.signup-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  position: relative;
}

h1 {
  margin: 0.625rem 0;
}

a {
  color: #1E5EFF;
  margin-left: 0.625rem;
  display: block;
  text-decoration: none;
}

.signup-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 540px;
  margin-bottom: 0.625rem;
  position: absolute;
  top: 0;
  border-radius: 20px;
  background-color: #fff;
}

.signup-body {
  width: 100%;
  overflow: scroll;
  margin-top: 80px;
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

.text-wrap {
  display: flex;
  justify-content: center;
  gap: 10px;
  width: 100%;

  span {
    text-align: end;
  }
}
</style>