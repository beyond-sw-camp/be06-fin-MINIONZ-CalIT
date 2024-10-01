export const weekSettingUtils = () => {
    const today = new Date();
    const startDate = new Date(today.setDate(today.getDate() - today.getDay()));
    const endDate = new Date(today.setDate(startDate.getDate() + 6));
    return {
        startDate: startDate.toISOString().split('T')[0] + 'T00:00:00',
        endDate: endDate.toISOString().split('T')[0] + 'T00:00:00'
    };
};