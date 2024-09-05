<script setup>
import UserButton from "@/view/user/component/UserButton.vue";
import UserInput from "@/view/user/component/UserInput.vue";
</script>

<template>
  <div class="password-page">
    <div class="password-header">
      <h1>비밀번호</h1>
      <div>
        <span>비밀번호를 초기화 할 수 있도록 이메일을 입력해주세요</span>
      </div>
    </div>
    <form @submit.prevent="password">
      <UserInput input-placeholder="이메일을 입력하세요" label="이메일"/>
      <router-link to="/dashboard">
        <UserButton type="submit" button-ment="비밀번호 초기화 링크 전송"></UserButton>
      </router-link>
    </form>
    <div>
      <router-link to="/find-password">비밀번호 찾기</router-link>
    </div>
  </div>
</template>

<style scoped>
.password-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
h1{
  margin: 0;
}
a{
  color: #1E5EFF;
  margin-left: 10px;
}
.password-header{
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