import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

export const useDashboardStore = defineStore('mypageStore', () => {
    const mySprintData = ref([]);
    const getMyKanban = async () => {
        const response = await axios.get('/api/sprint/all/my');
        mySprintData.value = response.data;
    };

    const getMyMonthly = async ({startdate, enddate}) => {
        const response = await axios.get(`/api/myMonthly?startdate=${startdate}&enddate=${enddate}`);
        mySprintData.value = response.data;
    }

    const getMyWeekly = async ({startdate, enddate}) => {
        const response = await axios.get(`/api/myWeekly?startdate=${startdate}&enddate=${enddate}`);
        mySprintData.value = response.data;
    }

    const getMyDashboard = async () => {
        const response = await axios.get('/api/myDashBoard');
        mySprintData.value = response.data;
    }

    return {
        mySprintData,
        getMyKanban,
        getMyMonthly,
        getMyWeekly,
        getMyDashboard
    };
})