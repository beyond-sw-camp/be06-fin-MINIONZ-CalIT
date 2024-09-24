<script setup>
import {ref, computed, onMounted} from 'vue';
import { useRouter } from 'vue-router';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";
import SocialLogin from "@/view/user/component/SocialLogin.vue";
import axios from "axios";
import { Notyf } from 'notyf';
import PerfectScrollbar from "perfect-scrollbar";
import 'perfect-scrollbar/css/perfect-scrollbar.css';

onMounted(() => {
  const container = document.querySelector('.signup-page');
  new PerfectScrollbar(container);
});

const router = useRouter();
const notyf = new Notyf();

const username = ref('');
const loginId = ref('');
const password = ref('');
const confirmPassword = ref('');
const email = ref('');
const showVerificationCode = ref(false);
const verificationCode = ref('');

//checkAll은 값을 통과하지 못하면 signup 버튼이 disabled 되게 하기 위함
const checkAll = computed(() => {
  return username.value.trim() !== '' && loginId.value.trim() !== '' && password.value.trim() !== '' && confirmPassword.value.trim() !== '' && email.value.trim() !== '';
});

const signup = () => {
  if (checkAll.value) {
    try {
      axios.post('/api/user/signup', {
        username: username.value,
        loginId: loginId.value,
        password: password.value,
        email: email.value
      });
      notyf.success('회원가입에 성공하였습니다.');
      return router.push('/user/complete');
    } catch (error) {
      console.error('Signup failed', error);
      notyf.error('회원가입 실패');
    }
  } else {
    notyf.error('값을  채우쎄용~')
  }
}
const checkId = async (loginId) => {
    try {
      const r = await axios.post('/api/user/check-id', {
        loginId: loginId,
      });
      console.log(r.data);
      if (r.data) {
        notyf.success('사용 가능한 아이디 입니다.');
      } else {
        notyf.error("중복된 아이디 입니다.")
      }
    } catch (error) {
      console.error('Check ID failed', error);
      notyf.error('이미 사용중인 아이디 입니다.');
    }
}


const showVerificationInput = async (email) => {

  try {
    const r = await axios.post('/api/user/send-verification-code', {
      email: email
    });
    if(r.data){ // 등록 가능 이메일 여부 true 면
      notyf.success('인증 코드를 전송하였습니다.');
      showVerificationCode.value = true;
    } else { //등록 가능 이메일 여부 false 면
      notyf.error("중복된 이메일 입니다.");
    }
  } catch (error) {
    console.error('Verification failed', error);
    notyf.error('인증 코드 전송에 실패하였습니다.');
  }
};

const confirmCode = (uuid) => {
  try {
    axios.post('/api/user/confirm-verification-code', {
      uuid: uuid
    });
    notyf.success('이메일 인증에 성공하였습니다.');
  } catch (error) {
    console.error('Code confirmation failed', error);
    notyf.error('인증에 실패하였습니다.');
  }
};

// const isLoginIdEntered = computed(() => {
//   return email.value.trim() !== '';
// });

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
    </div>
    <div class="signup-body">
      <form @submit.prevent="signup">
        <UserInput v-model="username" input-placeholder="이름을 입력하세요" label="이름" type="text"/>
        <div class="verify">
          <UserInput v-model="loginId" input-placeholder="아이디를 입력하세요" label="아이디" type="text"/>
          <button type="button" @click="checkId(loginId)" class="btn-verify" >중복 확인</button>
        </div>
        <div class="verify">
          <UserInput v-model="email" input-placeholder="이메일을 입력하세요" label="이메일" type="email"/>
          <button type="button" @click="showVerificationInput(email)"  class="btn-verify" :disabled="!isEmailEntered">인증 하기</button>
        </div>
        <div v-if="showVerificationCode" class="verify">
          <UserInput v-model="verificationCode" input-placeholder="인증 번호를 입력하세요" label="인증 번호" type="text"/>
          <button type="button" :disabled="!isVerificationCodeEntered" @click="confirmCode(verificationCode)" class="btn-verify">코드 입력</button>
        </div>
        <UserInput v-model="password" input-placeholder="비밀번호를 입력하세요" label="비밀번호" type="password"/>
        <UserInput v-model="confirmPassword" input-placeholder="비밀번호를 다시 입력하세요" label="비밀번호 확인" type="password"/>
        <p v-if="!passwordsMatch" class="error-message">비밀번호가 일치하지 않습니다.</p>

        <div @click="signup" style="margin: 0">
          <UserButton type="submit" button-ment="Sign up" disabled="!checkAll"></UserButton>
        </div>
      </form>
      <div class="text-wrap">
        <router-link to="/user/login">로그인 하기</router-link>
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
  position: relative;
  overflow-y: auto;
  overflow-x: hidden;
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