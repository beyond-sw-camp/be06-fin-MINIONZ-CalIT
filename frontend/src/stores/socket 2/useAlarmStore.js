import { defineStore } from 'pinia';

export const useAlarmStore = defineStore('alarm', {
    state: () => ({
        alarms: [],
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