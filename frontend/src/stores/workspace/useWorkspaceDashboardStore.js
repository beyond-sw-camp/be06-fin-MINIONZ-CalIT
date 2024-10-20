import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { weeklySettingUtils } from '@/utils/scheduleDateSettingUtils';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useWorkspaceDashboardStore = defineStore(
  'workspaceDashboardStore',
  () => {
    const workspaceSprintData = ref([]);
    const workspaceMonthlyData = ref([]);
    const workspaceWeeklyData = ref([]);
    const workspaceDashboardData = ref(null);

    const getWorkspaceKanban = async (workspaceId) => {
      const response = await axiosInstance.get(
        `/api/sprint/all/${workspaceId}`,
        { withCredentials: true }
      );

      if (response.data.success) {
        workspaceSprintData.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    };

    const getWorkspaceMonthly = async ({ workspaceId, startDate, endDate }) => {
      const response = await axiosInstance.get(
        `/api/schedule/${workspaceId}/monthly?startDate=${startDate}&endDate=${endDate}`,
        { withCredentials: true }
      );

      if (response.data.success) {
        workspaceMonthlyData.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    };

    const getWorkspaceWeekly = async ({workspaceId, startDate, endDate}) => {
      const response = await axiosInstance.get(
        `/api/schedule/${workspaceId}/weekly?startDate=${startDate}&endDate=${endDate}`,
        { withCredentials: true }
      );

      if (response.data.success) {
        workspaceWeeklyData.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    };

    const getWorkspaceDashboard = async (workspaceId) => {
      const response = await axiosInstance.get(
        `/api/dashboard/${workspaceId}`,
        { withCredentials: true }
      );

      if (response.data.success) {
        workspaceDashboardData.value = response.data.result;
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    };

    return {
      workspaceSprintData,
      workspaceMonthlyData,
      workspaceWeeklyData,
      workspaceDashboardData,
      getWorkspaceKanban,
      getWorkspaceMonthly,
      getWorkspaceWeekly,
      getWorkspaceDashboard,
    };
  }
);
