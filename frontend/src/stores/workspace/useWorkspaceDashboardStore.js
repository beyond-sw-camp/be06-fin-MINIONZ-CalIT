import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { weekSettingUtils } from '@/utils/weekSettingUtils';

export const useWorkspaceDashboardStore = defineStore('workspaceDashboardStore', () => {
    const workspaceSprintData = ref([]);
    const workspaceMonthlyData = ref([]);
    const workspaceWeeklyData = ref([]);
    const workspaceDashboardData = ref(null);

    const getWorkspaceKanban = async (workspaceId) => {
        const response = await axiosInstance.get(`/api/sprint/all/${workspaceId}`, { withCredentials: true });
        workspaceSprintData.value = response.data.result;
    };

    const getWorkspaceMonthly = async (workspaceId) => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axiosInstance.get(`/api/schedule/${workspaceId}/monthly?startDate=${startDate}&endDate=${endDate}`, { withCredentials: true });
        workspaceMonthlyData.value = response.data.result;
    };

    const getWorkspaceWeekly = async (workspaceId) => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axiosInstance.get(`/api/schedule/${workspaceId}/weekly?startDate=${startDate}&endDate=${endDate}`, { withCredentials: true });
        workspaceWeeklyData.value = response.data.result;
    };

    const getWorkspaceDashboard = async (workspaceId) => {
        const response = await axiosInstance.get(`/api/dashboard/${workspaceId}`, { withCredentials: true });
        workspaceDashboardData.value = response.data.result;
    };

    return {
        workspaceSprintData,
        workspaceMonthlyData,
        workspaceWeeklyData,
        workspaceDashboardData,
        getWorkspaceKanban,
        getWorkspaceMonthly,
        getWorkspaceWeekly,
        getWorkspaceDashboard
    };
});