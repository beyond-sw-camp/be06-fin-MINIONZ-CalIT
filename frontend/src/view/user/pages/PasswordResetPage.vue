<script setup>
import { ref, computed } from 'vue';
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";

const newPassword = ref('');
const confirmPassword = ref('');

const passwordsMatch = computed(() => {
  return newPassword.value === confirmPassword.value;
});

const resetPassword = () => {
  if (passwordsMatch.value) {
    console.log('Password reset successful');
  } else {
    console.log('Passwords do not match');
  }
};
</script>

<template>
  <div class="password-page">
    <div class="password-header">
      <h1>비밀번호 초기화</h1>
      <div>
        <span>새로운 비밀번호를 입력해주세요</span>
      </div>
    </div>
    <form @submit.prevent="resetPassword">
      <UserInput v-model="newPassword" input-placeholder="비밀번호를 입력하세요" label="새 비밀번호" type="password"/>
      <UserInput v-model="confirmPassword" input-placeholder="비밀번호를 동일하게 입력해주세요" label="비밀번호 확인" type="password"/>
      <p v-if="!passwordsMatch" class="error-message">! 비밀번호가 일치하지 않습니다.</p>
      <div>
        <UserButton type="submit" button-ment="비밀번호 변경"></UserButton>
        <router-link to="/user/login">
          <UserButton button-ment="로그인으로 돌아가기" class="btn-ver2"></UserButton>
        </router-link>
      </div>
    </form>
  </div>
</template>

<style scoped>
.password-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
h1 {
  margin: 0;
}
a {
  color: #1E5EFF;
  margin-left: 10px;
}
.password-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-bottom: 10px;
}
form {
  margin: 10px 0;
  display: flex;
  flex-direction: column;
  gap: 15px;
  width: 100%;
  a {
    height: 100%;
    display: block;
    margin: 0;
  }
}
p {
  margin: 0;
  color: #5a607f;
}
hr {
  border: 1px solid #D7DBEC;
  width: 100%;
}
.btn-ver2 {
  width: 100%;
  color: #1E5EFF;
  background-color: #fff;
  border: 1px solid #d9e1ec;
  text-decoration: none;
}
.error-message {
  background-color: #fff1f1;
  padding: 10px;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
}
</style>