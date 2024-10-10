import { defineStore } from 'pinia';
import {ref} from "vue";
import { axiosInstance } from '@/utils/axiosInstance';

export const useAlarmStore = defineStore('alarmStore', () => {
    const alarms = ref([]);
    const getAlarmData = async () => {
        alarms.value = [];
        try {
            const response = await axiosInstance.get('/api/alarm/my');
            alarms.value = response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    };

    const deleteAlarm = async (userAlarmId) => {
        try {
            console.log("alarmId : " + userAlarmId);
            await axiosInstance.delete(`/api/alarm/delete/${userAlarmId}`);
            alarms.value = alarms.value.filter(alarm => alarm.status !== 1);
        }
        catch (error) {
            console.error(error);
        }
    }

    return {
        alarms,
        getAlarmData,
        deleteAlarm
    };
})