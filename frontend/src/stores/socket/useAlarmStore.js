import axios from "axios";
import { defineStore } from 'pinia';
// import {alarmData} from "@/static/alarmData";
import {ref} from "vue";

export const useAlarmStore = defineStore('alarmStore', () => {
    const alarms = ref([]);
    const getAlarmData = async (userId) => {
        alarms.value = [];
        try {
            const response = await axios.get(`/api/alarm/connect/${userId}`);
            alarms.value = response.data;
        }
        catch (error) {
            console.error(error);
        }
    };

    const deleteAlarm = async (alarmId) => {
        try {
            await axios.delete(`/api/alarm/${alarmId}`);
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