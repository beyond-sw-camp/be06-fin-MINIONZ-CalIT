import {axiosInstance} from "@/utils/axiosInstance";

export const getBurndownData = async (workspaceId, sprintId) => {
    try {
        const sprintResponse = await axiosInstance.get(`/api/sprint/${workspaceId}/${sprintId}`);
        const sprint = sprintResponse.data?.result;

        const progressResponse = await axiosInstance.get(`/api/dashboard/${workspaceId}`);
        const progress = progressResponse.data?.result?.progress;

        const taskResponse = await axiosInstance.get(`/api/task/${sprintId}/all/status`);
        const tasks = taskResponse.data?.result;

        const doneResponse = await axiosInstance.get(`/api/task/${sprintId}/all`);
        const doneTasks = doneResponse.data?.result;

        return {
            sprint,
            allTaskCount: progress?.allTaskCount,
            doneTaskCount: tasks.filter(task => task.status === 'DONE').length,
            doneStoryPoints: doneTasks.map(task => task.endDate)
        };
    } catch (error) {
        console.error('Error fetching burndown data:', error);
        throw error;
    }
};

export const calculateBurndownData = (tasks, sprintStartDate, sprintEndDate) => {
    const totalTasks = tasks.length;
    console.log( '테스트', sprintStartDate, sprintEndDate);
    const totalDays = (new Date(sprintEndDate) - new Date(sprintStartDate)) / (1000 * 60 * 60 * 24);

    const idealData = [];
    for (let i = 0; i <= totalDays; i++) {
        idealData.push(totalTasks - (totalTasks / totalDays) * i);
    }

    const actualDataMap = tasks.reduce((acc, task) => {
        const date = new Date(task.updatedAt).toLocaleDateString();
        acc[date] = acc[date] || 0;
        if (task.status === 'DONE') {
            acc[date] += 1;
        }
        return acc;
    }, {});

    const actualData = [];
    let remainingTasks = totalTasks;
    for (let i = 0; i <= totalDays; i++) {
        const date = new Date(new Date(sprintStartDate).getTime() + i * 24 * 60 * 60 * 1000).toLocaleDateString();
        remainingTasks -= actualDataMap[date] || 0;
        actualData.push(remainingTasks);
    }

    return { idealData, actualData };
};