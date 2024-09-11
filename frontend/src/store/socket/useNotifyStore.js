import { defineStore } from 'pinia';

export const useNotificationStore = defineStore('notification', {
    state: () => ({
        notifications: [],
    }),
    actions: {
        addNotification(notification) {
            this.notifications.push(notification);
        },
        clearNotifications() {
            this.notifications = [];
        },
    },
});