export const priorityClass = (priority) => {
    return {
        'high-priority': priority === 'High Priority',
        'med-priority': priority === 'Med Priority',
    };
};