import { format } from 'date-fns';
import * as dateFns from "date-fns";

export function formatUtil(date, formatString) {
    return format(date, formatString);
}

export function getWeekDaysUtil(date) {
    const startOfWeek = dateFns.startOfWeek(date, { weekStartsOn: 0 }); // 일요일 시작
    const weekDays = [];
    for (let i = 0; i < 7; i++) {
        weekDays.push(dateFns.addDays(startOfWeek, i));
    }
    return weekDays;
}
export function getDaysInMonthUtil(date) {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
}

export function startOfMonthUtil(date) {
    return new Date(date.getFullYear(), date.getMonth(), 1);
}
// export const formatUtil = (date, dateFormat) => format(date, dateFormat);