import '@/assets/style/styles.css'

import { createApp } from 'vue'
import {createPinia} from "pinia";
import App from './App.vue'
import router from './router'

const app = createApp(App);
const pinia = createPinia();


router.onError((error) => {
    console.error('Router error:', error);
});
app.use(pinia)
app.use(router)
app.mount('#app')