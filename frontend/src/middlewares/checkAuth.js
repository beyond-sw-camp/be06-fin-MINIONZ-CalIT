export default function checkAuth(to, from, next) {
    const isAuthenticated = !!localStorage.getItem('authToken');

    if (isAuthenticated) {
        next();
    } else {
        next('/login');
    }
}