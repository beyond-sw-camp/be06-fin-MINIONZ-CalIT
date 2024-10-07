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
    }
};