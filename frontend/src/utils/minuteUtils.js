export const validateTime = (minuteInfo) => {
    const date = new Date(minuteInfo);
    const minutes = date.getMinutes();

    if (minutes % 10 !== 0) {
        const correctedMinutes = Math.floor(minutes / 10) * 10;
        date.setMinutes(correctedMinutes);
    }

    return date.toISOString().slice(0, 16);
};