import { defineStore } from 'pinia';
import {ref} from "vue";
import { axiosInstance } from '@/utils/axiosInstance';

export const useAlarmStore = defineStore('alarmStore', () => {
    const alarms = ref([]);
    const getAlarmData = async () => {
        alarms.value = [];
        try {
            const response = await axiosInstance.get('api/alarm/my');
            alarms.value = response.data;
        }
        catch (error) {
            console.error(error);
        }
    };

    const deleteAlarm = async (alarmId) => {
        try {
            await axiosInstance.delete(`/api/alarm/${alarmId}`);
            alarms.value = alarms.value.filter(alarm => alarm.id !== alarmId);
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