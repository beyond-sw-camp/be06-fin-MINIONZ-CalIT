import { ref } from 'vue';
import { jwtDecode } from 'jwt-decode';

export const useUserStore = () => {
  const token = ref(null);
  const user = ref(null);
  const isLoggedIn = ref(false);

  const setToken = (newToken) => {
    token.value = newToken;

    if (newToken) {
      const userInfo = extractUserInfoFromToken(newToken);
      if (userInfo) {
        setUser(userInfo);
        isLoggedIn.value = true;
      } else {
        logout();
      }
    } else {
      isLoggedIn.value = false;
      logout();
    }
  };

  const setUser = (userInfo) => {
    if (userInfo) {
      user.value = userInfo;
      sessionStorage.setItem('userInfo', JSON.stringify(userInfo));
    } else {
      console.error('userInfo is null or undefined');
    }
  };

  const getUserInfoFromSession = () => {
    const userInfoString = sessionStorage.getItem('userInfo');
    if (userInfoString) {
      try {
        const userInfo = JSON.parse(userInfoString);
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
    token.value = null;
    user.value = null;
    isLoggedIn.value = false;
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
        userName: decodedToken.userName,
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
    logout,
  };
};
