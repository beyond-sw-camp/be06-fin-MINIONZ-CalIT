// 채팅, 알람에서 쓰는 몇분전
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

