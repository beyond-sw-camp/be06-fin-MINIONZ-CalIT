import '@/assets/style/styles.css'
import 'vuetify/styles';
import 'notyf/notyf.min.css';

import { createApp } from 'vue'
import {createPinia} from "pinia";
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';

const app = createApp(App);
const pinia = createPinia();


router.onError((error) => {
    console.error('Router error:', error);
});
app.use(pinia)
app.use(router)
app.use(vuetify)
app.mount('#app')