import { ref } from 'vue';
import { jwtDecode } from 'jwt-decode';

export const useUserStore = () => {
    const token = ref(null);
    const user = ref(null);
    const isLoggedIn = ref(false);

    const setToken = (newToken) => {
        console.log('setToken called with:', newToken);
        token.value = newToken;

        if (newToken) {

            const userInfo = extractUserInfoFromToken(newToken);
            if (userInfo) {
                console.log('User info extracted successfully:', userInfo);
                setUser(userInfo);
                isLoggedIn.value = true;
            } else {
                console.log('Invalid or expired token');
                logout();
            }
        } else {
            console.log('No token provided, logging out');
            isLoggedIn.value = false;
            logout();
        }
    };

    const setUser = (userInfo) => {
        console.log('setUser called with:', userInfo);
        if (userInfo) {
            user.value = userInfo;
            console.log('User set to:', user.value);
            sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
        } else {
            console.error('userInfo is null or undefined');
        }
    };

    const getUserInfoFromSession = () => {
        const userInfoString = sessionStorage.getItem('userInfo');
        console.log('Retrieved userInfo from sessionStorage:', userInfoString);
        if (userInfoString) {
            try {
                const userInfo = JSON.parse(userInfoString);
                console.log('Parsed userInfo:', userInfo);
                return userInfo;
            } catch (error) {
                console.error('Failed to parse userInfo:', error);
                return null;
            }
        } else {
            console.error('No userInfo found in sessionStorage');
            return null;
        }
    };

    const userInfo = getUserInfoFromSession();
    setUser(userInfo);

    const logout = () => {
        console.log('Logging out');
        token.value = null;
        user.value = null;
        isLoggedIn.value = false;
        sessionStorage.removeItem('userInfo');
    };

    const extractUserInfoFromToken = (token) => {
        try {
            console.log('extractUserInfoFromToken called with:', token);

            const decodedToken = jwtDecode(token);
            console.log('Decoded Token:', decodedToken);

            const currentTime = Math.floor(Date.now() / 1000);
            console.log('Current Time:', currentTime, 'Token Expiry Time:', decodedToken.exp);

            if (decodedToken.exp < currentTime) {
                console.log('Token has expired');
                throw new Error('Token has expired');
            }

            console.log('Token is valid, extracting user info');
            return {
                idx: decodedToken.idx,
                loginId: decodedToken.loginId,
                role: decodedToken.role,
                userName: decodedToken.userName
            };
        } catch (error) {
            console.error('Failed to decode or validate token:', error);
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