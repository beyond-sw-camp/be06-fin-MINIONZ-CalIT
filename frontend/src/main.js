import '@/assets/style/styles.css'
import 'vuetify/styles';
import 'notyf/notyf.min.css';

import { createApp } from 'vue'
import {createPinia} from "pinia";
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
// import { Notyf } from 'notyf';
// import { useUserStore} from "@/stores/user/useUserStore";

const app = createApp(App);
const pinia = createPinia();
//
// const notyf = new Notyf()
// const userStore = useUserStore();
// const userId = userStore.user.value?.idx;
// let eventSource;
// function connectEventSource() {
//     if (eventSource) {
//         eventSource.close(); // 기존 연결 종료
//     }
//
//     eventSource = new EventSource(`https://calit.kro.kr/alarm/connect/${userId}`);
//
//     eventSource.onmessage = (event) => {
//         console.log('New message:', event.data);
//         console.log('메세지가 도착했어요!');
//         const data = JSON.parse(event.data);
//         notyf.success(data.AlarmContents);
//     };
//
//     eventSource.onerror = () => {
//         console.error('SSE 연결이 끊어졌습니다. 재연결 시도 중...');
//         eventSource.close(); // 현재 연결 종료
//         setTimeout(connectEventSource, 1000); // 1초 후 재연결 시도
//     };
// }
//
// connectEventSource();

router.onError((error) => {
    console.error('Router error:', error);
});
app.use(pinia)
app.use(router)
app.use(vuetify)
app.mount('#app')