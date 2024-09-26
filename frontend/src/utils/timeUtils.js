import {formatUtil} from "@/utils/dateUtils";

export function getTimeDifference(time) {
    const now = new Date();
    const alarmDate = new Date(time);
    const diffMs = now - alarmDate;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMins / 60);
    const diffDays = Math.floor(diffHours / 24);

    if (diffMins < 60) {
        return `${diffMins} min`;
    } else if (diffHours < 24) {
        return `${diffHours} hr`;
    } else {
        return `${diffDays} days`;
    }
}

export function getWeekRange(today) {
    const start = new Date(today);
    const end = new Date(today);
    end.setDate(today.getDate() + 6);
    return `${formatUtil(start, 'MMM d')} - ${formatUtil(end, 'MMM d')}`;
}