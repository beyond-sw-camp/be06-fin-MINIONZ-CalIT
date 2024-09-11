<script setup>
import UserButton from "@/view/user/component/UserButton.vue";
import userComplete from "@/assets/img/userComplete.svg";
</script>

<template>
  <div class="password-page">
    <div class="password-header">
      <img :src="userComplete" alt="Complete">
      <h1>회원가입 완료</h1>
      <div>
        <span>비밀번호를 초기화 할 수 있도록 이메일을 입력해주세요</span>
      </div>
    </div>
    <div style="width: 100%">
      <router-link to="/my/dashboard" style="text-decoration: none;">
        <UserButton button-ment="대시보드로 돌아가기" class="btn-ver2"></UserButton>
      </router-link>
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
p{
  margin: 0;
  color: #5a607f;
}
hr{
  border: 1px solid #D7DBEC;
  width: 100%;
}
.btn-ver2{
  width: 100%;
  color: #1E5EFF;
  background-color: #fff;
  border: 1px solid #d9e1ec;
  text-decoration: none;
}
</style>