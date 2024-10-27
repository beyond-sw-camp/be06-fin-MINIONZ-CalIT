import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { weeklySettingUtils } from '@/utils/scheduleDateSettingUtils';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useMyDashboardStore = defineStore('mypageStore', () => {
  const mySprintData = ref([]);
  const getMyKanban = async () => {
    const response = await axiosInstance.get('/api/task/my/all');

    if (response.data.success) {
      mySprintData.value = response.data.result;
    } else {
      notyf.error(response.data.message);
    }
  };

  const getMyMonthly = async ({ startDate, endDate }) => {
    const response = await axiosInstance.get(
      `/api/schedule/my/monthly?startDate=${startDate}&endDate=${endDate}`
    );

    if (response.data.success) {
      mySprintData.value = response.data.result;
    } else {
      notyf.error(response.data.message);
    }
  };

  const getMyWeekly = async ({startDate, endDate}) => {
    const response = await axiosInstance.get(
      `/api/schedule/my/weekly?startDate=${startDate}&endDate=${endDate}`
    );

    if (response.data.success) {
      mySprintData.value = response.data.result;
    } else {
      notyf.error(response.data.message);
    }
  };

  const getMyDashboard = async () => {
    const { startDate, endDate } = weeklySettingUtils();
    const response = await axiosInstance.get(
      `/api/dashboard/my?startDate=${startDate}&endDate=${endDate}`
    );

    if (response.data.success) {
      mySprintData.value = response.data.result;
    } else {
      notyf.error(response.data.message);
    }
  };

  return {
    mySprintData,
    getMyKanban,
    getMyMonthly,
    getMyWeekly,
    getMyDashboard,
  };
});
