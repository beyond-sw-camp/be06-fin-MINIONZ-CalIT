import { ref } from 'vue';
import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from 'pinia';
import { weeklySettingUtils, monthlySettingUtils } from "@/utils/dateSettingUtils";

export const useMyDashboardStore = defineStore('mypageStore', () => {
    const mySprintData = ref([]);
    const getMyKanban = async () => {
        const response = await axiosInstance.get('/api/task/my/all');
        mySprintData.value = response.data.result;
    };

    const getMyMonthly = async () => {
        const { startDate, endDate } = monthlySettingUtils;
        const response = await axiosInstance.get(`/api/schedule/my/monthly?startDate=${startDate}&endDate=${endDate}`);
        mySprintData.value = response.data.result;
    }

    const getMyWeekly = async () => {
        const { startDate, endDate } = weeklySettingUtils();
        const response = await axiosInstance.get(`/api/schedule/my/weekly?startDate=${startDate}&endDate=${endDate}`);
        mySprintData.value = response.data.result;
    }

    const getMyDashboard = async () => {
        const { startDate, endDate } = weeklySettingUtils();
        const response = await axiosInstance.get(`/api/dashboard/my?startDate=${startDate}&endDate=${endDate}`);
        mySprintData.value = response.data.result;
    }

    return {
        mySprintData,
        getMyKanban,
        getMyMonthly,
        getMyWeekly,
        getMyDashboard
    };
})
