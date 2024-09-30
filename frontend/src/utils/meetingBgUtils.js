export const getBackgroundColor = (date) => {
    if (date.startsWith('Today')) {
        return '#FFE3E3';
    } else if (date.startsWith('Tomorrow')) {
        return '#FFEEDE';
    } else {
        return '#DEE3FF';
    }
};