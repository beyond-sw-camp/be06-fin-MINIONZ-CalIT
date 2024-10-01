<script setup>
import naver from '@/assets/icon/social/naver.png';
import kakao from '@/assets/icon/social/kakao.png';
import { ref } from 'vue';
import axios from "axios";

// 환경변수 가져오기
const naverApiKey = ref(process.env.VUE_APP_NAVER_API_KEY);
const googleApiKey = ref(process.env.VUE_APP_GOOGLE_API_KEY);
const REDIRECT_URI = encodeURIComponent(process.env.VUE_APP_REDIRECT_URI);

// 상태 값 생성
function generateState() {
  return Math.random().toString(36).substr(2, 12); // 랜덤 문자열 생성
}

const STATE = ref(generateState());

// 새로운 창을 열어 URL로 이동하는 함수
const openInNewWindow = (url, provider) => {
  window.open(url, '_blank');
  window.addEventListener('message', (event) => {
    if (event.origin !== window.location.origin) return;
    const { token } = event.data;
    if (token) {
      localStorage.setItem(`${provider}_token`, token);
    }
  });
};

// 구글 로그인 URL 생성
const googleLoginUrl = (`https://accounts.google.com/o/oauth2/v2/auth?client_id=${googleApiKey.value}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=email profile`);

// 네이버 로그인 URL 생성
const naverLoginUrl = ref(`https://nid.naver.com/oauth2.0/authorize?client_id=${naverApiKey.value}&response_type=code&redirect_uri=${REDIRECT_URI}&state=${STATE.value}`);

// 카카오 로그인 URL 생성
const kakaoLoginUrl = ref(`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`);

const sendGoogleRequest = async () => {
  try {
    const response = await axios.get('/oauth2/authorization/google');
    console.log(response.data);
  } catch (error) {
    console.error('Error sending request to Kakao:', error);
  }
};

const sendNaverRequest = async () => {
  try {
    const response = await axios.get('/oauth2/authorization/naver');
    console.log(response.data);
  } catch (error) {
    console.error('Error sending request to Kakao:', error);
  }
};

const sendKakaoRequest = async () => {
  try {
    const response = await axios.get('/oauth2/authorization/kakao');
    console.log(response.data);
  } catch (error) {
    console.error('Error sending request to Kakao:', error);
  }
};
</script>

<template>
  <div>
    <hr>
    <div class="social-login">
      <div class="social-login_title">소셜 로그인</div>
      <div class="social-login_content">
        <!-- 구글 로그인 버튼 -->
        <div
            @click="openInNewWindow(googleLoginUrl, 'google'); sendGoogleRequest()"
            class="social-login_content_item"
        >
          <div class="social-login_content_item_icon">
            <img src="https://img.icons8.com/color/48/000000/google-logo.png" alt="구글로고"/>
          </div>
          <div class="social-login_content_item_text">구글 로그인</div>
        </div>

        <!-- 네이버 로그인 버튼 -->
        <div
            @click="openInNewWindow(naverLoginUrl, 'naver'); sendNaverRequest()"
            class="social-login_content_item"
        >
          <div class="social-login_content_item_icon">
            <img :src="naver" alt="naver logo"/>
          </div>
          <div class="social-login_content_item_text">네이버 로그인</div>
        </div>

        <!-- 카카오 로그인 버튼 -->
        <div
            @click="openInNewWindow(kakaoLoginUrl, 'kakao'); sendKakaoRequest()"
            class="social-login_content_item"
        >
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
  cursor: pointer;
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