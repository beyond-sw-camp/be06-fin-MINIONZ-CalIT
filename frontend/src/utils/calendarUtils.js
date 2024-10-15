import { ref, computed } from 'vue';
import { getDaysInMonthUtil, startOfMonthUtil } from '@/utils/scheduleDateFnsUtils';

export const useCalendar = () => {
    const today = new Date();
    const currentYear = ref(today.getFullYear());
    const currentMonth = ref(today.getMonth());
    const weekDays = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];

    const daysInMonth = computed(() => getDaysInMonthUtil(new Date(currentYear.value, currentMonth.value)));
    const startBlankDays = computed(() => startOfMonthUtil(new Date(currentYear.value, currentMonth.value)).getDay());

    const goToToday = () => {
        currentYear.value = today.getFullYear();
        currentMonth.value = today.getMonth();
    };

    const prevMonth = (currentDate) => {
        const date = new Date(currentDate);
        date.setMonth(date.getMonth() - 1);
        return {
            startDate: new Date(date.getFullYear(), date.getMonth(), 1),
            endDate: new Date(date.getFullYear(), date.getMonth() + 1, 0)
        };
    };

    const nextMonth = (currentDate) => {
        const date = new Date(currentDate);
        date.setMonth(date.getMonth() + 1);
        return {
            startDate: new Date(date.getFullYear(), date.getMonth(), 1),
            endDate: new Date(date.getFullYear(), date.getMonth() + 1, 0)
        };
    };

    return {
        today,
        currentYear,
        currentMonth,
        weekDays,
        daysInMonth,
        startBlankDays,
        goToToday,
        prevMonth,
        nextMonth
    };
};