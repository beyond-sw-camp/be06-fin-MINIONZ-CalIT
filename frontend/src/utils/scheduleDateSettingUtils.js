// schedule에서 시작날짜 끝날짜 설정하는 로직
export const weeklySettingUtils = () => {
    const today = new Date();
    const startDate = new Date(today.setDate(today.getDate() - today.getDay()));
    const endDate = new Date(today.setDate(startDate.getDate() + 6));
    return {
        startDate: startDate.toISOString().split('T')[0] + 'T00:00:00',
        endDate: endDate.toISOString().split('T')[0] + 'T00:00:00'
    };
};

export const monthlySettingUtils = () => {
    const today = new Date(new Date().toLocaleString('en-US', { timeZone: 'Asia/Seoul' }));
    const startDate = new Date(today.getFullYear(), today.getMonth(), 1 + 1, 0, 0, 0, 0);
    const endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0, 23, 59, 59, 999);
    return {
        startDate: startDate.toISOString().split('T')[0] + 'T00:00:00',
        endDate: endDate.toISOString().split('T')[0] + 'T00:00:00'
    };
};