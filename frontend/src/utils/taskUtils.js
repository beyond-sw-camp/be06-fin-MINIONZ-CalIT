export const getTaskCountBackgroundColor = (status) => {
    if (status === 'No Status') return 'rgba(234, 179, 8, 0.1)';
    else if (status === 'To Do') return 'rgba(236, 72, 153, 0.1)';
    else if (status === 'In Progress') return 'rgba(168, 85, 247, 0.1)';
    else return 'rgba(34, 197, 94, 0.1)';
}

export const getTaskCountColor = (status) => {
    switch (status) {
        case 'NO_STATUS':
            return { color: 'rgba(234, 179, 8)', text: 'No Status' };
        case 'TODO':
            return { color: 'rgba(236, 72, 153)', text: 'To Do' };
        case 'IN_PROGRESS':
            return { color: 'rgba(168, 85, 247)', text: 'In Progress' };
        case 'DONE':
            return { color: 'rgba(34, 197, 94)', text: 'Done' };
        default:
            return { color: 'rgba(34, 197, 94)', text: 'Done' };
    }
}