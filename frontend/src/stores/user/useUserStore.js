import {ref} from 'vue';
import {jwtDecode} from 'jwt-decode';

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
        if (userInfo)
            user.value = userInfo;
            sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
    };

    const getUserInfoFromSession = () => {
        const userInfoString = sessionStorage.getItem('userInfo');
        if (userInfoString) {
            try {
                return JSON.parse(userInfoString);
            } catch (error) {
                return null;
            }
        } else {
            return null;
        }
    };

    const userInfo = getUserInfoFromSession();
    setUser(userInfo);

    const logout = () => {
        token.value = null;
        user.value = null;
        isLoggedIn.value = false;
        sessionStorage.clear();
        sessionStorage.removeItem('userInfo');
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
            return null;
        }
    };

    return {
        token,
        user,
        isLoggedIn,
        setToken,
        setUser,
        logout
    };
};