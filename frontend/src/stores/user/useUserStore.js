import { ref } from 'vue';
import jwtDecode from 'jwt-decode';

export const useUserStore = () => {
    const token = ref(null);
    const user = ref(null);
    const isLoggedIn = ref(false);

    const setToken = (newToken) => {
        token.value = newToken;
        const userInfo = extractUserInfoFromToken(newToken);
        if (newToken && userInfo) {
            setUser(userInfo);
            isLoggedIn.value = true;
        } else {
            isLoggedIn.value = false;
            logout();
        }
    };

    const setUser = (userInfo) => {
        if (userInfo) {
            user.value = userInfo;
            sessionStorage.setItem('userInfo', JSON.stringify(userInfo)); // 유효한 userInfo만 저장
        } else {
            console.warn('Attempted to set null userInfo. Ignoring.');
        }
    };

    const getUserInfoFromSession = () => {
        const storedToken = sessionStorage.getItem('token');
        if (storedToken) {
            const userInfo = extractUserInfoFromToken(storedToken);
            if (userInfo) {
                setUser(userInfo);
                token.value = storedToken;
                isLoggedIn.value = true;
            } else {
                logout();
            }
        } else {
            logout();
        }
    };

    const logout = () => {
        token.value = null;
        user.value = null;
        isLoggedIn.value = false;
        sessionStorage.clear();
    };

    const extractUserInfoFromToken = (token) => {
        try {
            const decodedToken = jwtDecode(token);
            const currentTime = Math.floor(Date.now() / 1000);

            if (decodedToken.exp < currentTime) {
                throw new Error('Token has expired');
            }

            return {
                idx: decodedToken.idx,
                loginId: decodedToken.loginId,
                role: decodedToken.role,
                userName: decodedToken.userName
            };
        } catch (error) {
            console.error('Error decoding token:', error);
            return null;
        }
    };

    return {
        token,
        user,
        isLoggedIn,
        setToken,
        setUser,
        logout,
        getUserInfoFromSession,
    };
};