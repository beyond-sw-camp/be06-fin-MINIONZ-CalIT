import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

export const useDashboardStore = defineStore('mypageStore', () => {
    const workspaceSprintData = ref([]);
    const getWorkspaceKanban = async () => {
        const response = await axios.get('/api/sprint/all/workspace');
        workspaceSprintData.value = response.data;
    };

    const getWorkspaceMonthly = async ({startdate, enddate}) => {
        const response = await axios.get(`/api/workspaceMonthly?startdate=${startdate}&enddate=${enddate}`);
        workspaceSprintData.value = response.data;
    }

    const getWorkspaceWeekly = async ({startdate, enddate}) => {
        const response = await axios.get(`/api/workspaceWeekly?startdate=${startdate}&enddate=${enddate}`);
        workspaceSprintData.value = response.data;
    }

    const getWorkspaceDashboard = async (workspaceId) => {
        const response = await axios.get(`/api/dashboard/${workspaceId}`);
        workspaceSprintData.value = {
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