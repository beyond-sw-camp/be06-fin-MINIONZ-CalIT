import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        token: null,
        user: null,
        isLoggedIn: false,
    }),
    actions: {
        setToken(token) {
            this.token = token;
            this.isLoggedIn = !!token;
        },
        setUser(user) {
            this.user = user;
        },
        logout() {
            this.token = null;
            this.user = null;
            this.isLoggedIn = false;
            sessionStorage.removeItem('authToken');
        }
    }
});