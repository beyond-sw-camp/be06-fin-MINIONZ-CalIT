export const getTaskCountBackgroundColor = (status) => {
    if (status === 'No Status') return 'rgba(234, 179, 8, 0.1)';
    else if (status === 'To Do') return 'rgba(236, 72, 153, 0.1)';
    else if (status === 'In Progress') return 'rgba(168, 85, 247, 0.1)';
    else return 'rgba(34, 197, 94, 0.1)';
}

export const getTaskCountColor = (status) => {
    if (status === 'No Status') return 'rgba(234, 179, 8)';
    else if (status === 'To Do') return 'rgba(236, 72, 153)';
    else if (status === 'In Progress') return 'rgba(168, 85, 247)';
    else return 'rgba(34, 197, 94)';
}