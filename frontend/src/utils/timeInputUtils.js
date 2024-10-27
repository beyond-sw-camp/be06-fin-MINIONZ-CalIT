export const timeInputUtils = {
    adjustToNearestHalfHour(datetime) {
        const date = new Date(datetime);
        const minutes = date.getMinutes();
        date.setMinutes(minutes < 30 ? 0 : 30, 0, 0);
        return date.toISOString().slice(0, 16);
    },
    adjustTime(start, end) {
        const adjustedStart = this.adjustToNearestHalfHour(start);
        const adjustedEnd = this.adjustToNearestHalfHour(end);
        return { start: adjustedStart, end: adjustedEnd };
    },

    validateTime (minuteInfo) {
        const date = new Date(minuteInfo);
        const minutes = date.getMinutes();

        if (minutes % 10 !== 0) {
            const correctedMinutes = Math.floor(minutes / 10) * 10;
            date.setMinutes(correctedMinutes);
        }

        return date.toISOString().slice(0, 16);
    }
};