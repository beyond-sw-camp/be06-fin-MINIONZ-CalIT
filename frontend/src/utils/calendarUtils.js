import { ref, computed } from 'vue';
import { getDaysInMonthUtil, startOfMonthUtil } from '@/utils/dateUtils';

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

    const prevMonth = () => {
        if (currentMonth.value === 0) {
            currentMonth.value = 11;
            currentYear.value--;
        } else {
            currentMonth.value--;
        }
    };

    const nextMonth = () => {
        if (currentMonth.value === 11) {
            currentMonth.value = 0;
            currentYear.value++;
        } else {
            currentMonth.value++;
        }
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