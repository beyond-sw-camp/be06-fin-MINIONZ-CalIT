import { defineStore } from 'pinia';
import {alarmData} from "@/static/alarmData";

export const useAlarmStore = defineStore('alarm', {
    state: () => ({
        alarms: alarmData,
    }),
    actions: {
        addAlarm(alarm) {
            this.alarms.push(alarm);
        },
        clearAlarms() {
            this.alarms = [];
        },
    },
});