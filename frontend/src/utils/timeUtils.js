// 채팅, 알람에서 쓰는 몇분전


// 2024-10-23T14:11:22.748172 local
// 2024-10-23T05:12:00.822252 kube
export function getTimeDifference(time) {
    const now = new Date();
    const alarmDate = new Date(new Date(time).toLocaleString('en-US', { timeZone: 'Asia/Seoul' }));
    alarmDate.setHours(alarmDate.getHours() + 9);
    const diffMs = now - alarmDate;
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMins / 60);
    const diffDays = Math.floor(diffHours / 24);

    if (diffMins < 1) {
        return `now`;
    } else if (diffMins < 60) {
        return `${diffMins} min`;
    } else if (diffHours < 24) {
        return `${diffHours} hr`;
    } else {
        return `${diffDays} days`;
    }
}

export function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year} /${month}/ ${day}`;
}
