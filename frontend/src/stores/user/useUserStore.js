import { ref } from 'vue';
import { defineStore } from 'pinia';

export const useUserStore = defineStore('userStore', () => {
    const token = ref(null);
    const user = ref([]);
    const isLoggedIn = ref(false);

    const setToken = (newToken) => {
        token.value = newToken;
        isLoggedIn.value = !!newToken;
    };

    const setUser = (userInfo) => {
        user.value = userInfo;
        sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
    };

    const getUserInfo = () => {
        return user.value;
    };

    const logout = () => {
        token.value = null;
        user.value = null;
        isLoggedIn.value = false;
        sessionStorage.removeItem('authToken');
        sessionStorage.removeItem('userInfo');
    };

    return {
        token,
        user,
        isLoggedIn,
        setToken,
        setUser,
        getUserInfo,
        logout
    };
});