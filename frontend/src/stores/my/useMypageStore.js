import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';
import {weekSettingUtils} from "@/utils/weekSettingUtils";

export const useMypageStore = defineStore('mypageStore', () => {
    const mySprintData = ref([]);
    const getMyKanban = async () => {
        const response = await axios.get('/api/task/my/all');
        mySprintData.value = response.data;
    };

    const getMyMonthly = async () => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axios.get(`/api/my/monthly?startDate=${startDate}&endDate=${endDate}`);
        mySprintData.value = response.data;
    }

    const getMyWeekly = async () => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axios.get(`/api/myWeekly?startDate=${startDate}&endDate=${endDate}`);
        mySprintData.value = response.data;
    }

    const getMyDashboard = async () => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axios.get(`/api/dashboard/my?startDate=${startDate}&endDate=${endDate}`);
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
