// 먼슬리, 위클리에서 쓰는 date-fns 관련 유틸 함수들

import { format } from 'date-fns';
import * as dateFns from "date-fns";

export function formatUtil(date, formatString) {
    return format(date, formatString);
}

export function getWeekRange(today) {
    const start = new Date(today);
    const end = new Date(today);
    end.setDate(today.getDate() + 6);
    return `${formatUtil(start, 'MMM d')} - ${formatUtil(end, 'MMM d')}`;
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