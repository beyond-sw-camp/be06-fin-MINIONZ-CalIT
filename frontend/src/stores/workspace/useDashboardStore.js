import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';
import {weekSettingUtils} from "@/utils/weekSettingUtils";

export const useDashboardStore = defineStore('mypageStore', () => {
    const workspaceSprintData = ref([]);
    const workspaceMonthlyData = ref([]);
    const workspaceWeeklyData = ref([]);
    const workspaceDashboardData = ref([]);

    const getWorkspaceKanban = async (workspaceId) => {
        const response = await axios.get(`/api/sprint/all/${workspaceId}`);
        workspaceSprintData.value = response.data;
    };

    const getWorkspaceMonthly = async (workspaceId) => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axios.get(`/api/schedule/${workspaceId}/monthly?startDate=${startDate}&endDate=${endDate}`);
        workspaceMonthlyData.value = response.data;
    }

    const getWorkspaceWeekly = async () => {
        const { startDate, endDate } = weekSettingUtils();
        const response = await axios.get(`/api/workspaceWeekly?startDate=${startDate}&endDate=${endDate}`);
        workspaceWeeklyData.value = response.data;
    }

    const getWorkspaceDashboard = async (workspaceId) => {
        const response = await axios.get(`/api/dashboard/${workspaceId}`);
        workspaceDashboardData.value = {
            progress: {
                sprintCount: response.data.sprintCount,
                alltaskCount: response.data.alltaskCount,
                successtaskCount: response.data.successtaskCount,
                issueCount: response.data.issueCount
            },
            upcomingMeetings: response.data.upcomingMeetings
        };
    }

    return {
        workspaceSprintData,
        getWorkspaceKanban,
        getWorkspaceMonthly,
        getWorkspaceWeekly,
        getWorkspaceDashboard
    };
})
