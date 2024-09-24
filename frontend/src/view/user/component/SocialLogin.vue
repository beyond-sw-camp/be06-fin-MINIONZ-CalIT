<script setup>
import naver from '@/assets/icon/social/naver.png';
import kakao from '@/assets/icon/social/kakao.png';

// const googleApiKey = process.env.GOOGLE_API_KEY;
// const naverApiKey = process.env.NAVER_API_KEY;
// const kakaoApiKey = process.env.KAKAO_API_KEY;
// const REDIRECT_URI = process.env.REDIRECT_URI;

function generateState() {
  return Math.random().toString(36).substr(2, 12); // 랜덤 문자열 생성
}

const STATE = generateState();

const openInNewWindow = (url) => {
  window.open(url, '_blank');
};

</script>

<template>
  <div>
    <hr>
    <div class="social-login">
      <div class="social-login_title">소셜 로그인</div>
      <div class="social-login_content">
        <div
            @click="openInNewWindow(`https://accounts.google.com/o/oauth2/v2/auth?scope=email%20openid&response_type=code&redirect_uri=${REDIRECT_URI}&client_id=${googleApiKey}`)"
            class="social-login_content_item">
          <div class="social-login_content_item_icon">
            <img src="https://img.icons8.com/color/48/000000/google-logo.png" alt="구글로고"/>
          </div>
          <div class="social-login_content_item_text">구글 로그인</div>
        </div>
        <div
            @click="openInNewWindow(`https://nid.naver.com/oauth2.0/authorize?client_id=${naverApiKey}&response_type=code&redirect_uri=${REDIRECT_URI}&state=${STATE}`)"
            class="social-login_content_item">
          <div class="social-login_content_item_icon">
            <img :src="naver" alt="naver logo"/>
          </div>
          <div class="social-login_content_item_text">네이버 로그인</div>
        </div>
        <div
            @click="openInNewWindow('https://kauth.kakao.com/oauth/authorize?client_id=5c347f3e42c5232217cc650cd95a252a&redirect_uri=http://localhost:8081/user/signup&response_type=code&propmpt=login')"
            class="social-login_content_item">
          <div class="social-login_content_item_icon">
            <img :src="kakao" alt="kakao"/>
          </div>
          <div class="social-login_content_item_text">카카오 로그인</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
  color: #28303F;
}

hr {
  border: 1px solid #D7DBEC;
  width: 100%;
  margin: 10px 0;
  box-sizing: border-box;
}

.row {
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