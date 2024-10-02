import axios from 'axios';

export const axiosInstance = axios.create({
    withCredentials: true,
    headers: {
        'Authorization': `Bearer ${sessionStorage.getItem('ATOKEN')}`
    }
});