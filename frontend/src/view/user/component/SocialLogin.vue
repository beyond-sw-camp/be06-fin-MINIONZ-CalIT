<script setup>
import naver from '@/assets/icon/social/naver.png';
import kakao from '@/assets/icon/social/kakao.png';

const redirectToGoogle = () => {
  window.location.href = 'https://accounts.google.com/o/oauth2/auth?...';
};

const redirectToGithub = () => {
  window.location.href = 'https://github.com/login/oauth/authorize?...';
};

const redirectToNaver = () => {
  window.location.href = 'https://nid.naver.com/oauth2.0/authorize?...';
};

const redirectToKakao = () => {
  window.location.href = 'https://kauth.kakao.com/oauth/authorize?...';
};
</script>

<template>
  <div >
    <hr>
    <div class="social-login">
      <div class="social-login_title">소셜 로그인</div>
      <div class="social-login_content">
        <div class="row">
          <div class="social-login_content_item" @click="redirectToGoogle">
            <div class="social-login_content_item_icon">
              <img src="https://img.icons8.com/color/48/000000/google-logo.png" alt="구글로고"/>
            </div>
            <div class="social-login_content_item_text">구글 로그인</div>
          </div>
          <div class="social-login_content_item" @click="redirectToGithub">
            <div class="social-login_content_item_icon">
              <img src="https://img.icons8.com/color/48/000000/github.png" alt="깃허브로고"/>
            </div>
            <div class="social-login_content_item_text">깃허브 로그인</div>
          </div>
        </div>
        <div class="row">
          <div class="social-login_content_item" @click="redirectToNaver">
            <div class="social-login_content_item_icon">
              <img :src="naver" alt="naver logo"/>
            </div>
            <div class="social-login_content_item_text">네이버 로그인</div>
          </div>
          <div class="social-login_content_item" @click="redirectToKakao">
            <div class="social-login_content_item_icon">
              <img :src="kakao" alt="kakao"/>
            </div>
            <div class="social-login_content_item_text">카카오 로그인</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
hr{
  border: 1px solid #D7DBEC;
  width: 100%;
  margin: 10px 0;
  box-sizing: border-box;
}
.row{
  display: flex;
  gap: 10px;
}
.social-login {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.social-login_title {
  font-size: 15px;
  margin-bottom: 10px;
}
.social-login_content {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
  box-sizing: border-box;
  overflow: hidden;
}
.social-login_content_item {
  display: flex;
  gap: 10px;
  align-items: center;
  width: 100%;
  border: 1px solid #D7DBEC;
  border-radius: 4px;
  justify-content: center;
  padding: 10px;
}
.social-login_content_item_icon {
  width: 24px;
  height: 24px;
}
.social-login_content_item_icon img {
  width: 100%;
  height: 100%;
}
.social-login_content_item_text {
  font-size: 16px;
}
</style>