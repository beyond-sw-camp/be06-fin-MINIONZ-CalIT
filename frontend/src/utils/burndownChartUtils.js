import { axiosInstance } from "@/utils/axiosInstance";

export const getBurndownData = async (workspaceId, sprintId) => {
    try {
        const sprintResponse = await axiosInstance.get(`/api/sprint/${workspaceId}/${sprintId}`);
        const sprint = sprintResponse.data?.result;

        const progressResponse = await axiosInstance.get(`/api/dashboard/${workspaceId}`);
        const progress = progressResponse.data?.result?.progress;

        const taskResponse = await axiosInstance.get(`/api/task/${workspaceId}/${sprintId}/all/status`);
        const tasks = taskResponse.data?.result;

        const doneResponse = await axiosInstance.get(`/api/task/${workspaceId}/${sprintId}/all`);
        const doneTasks = doneResponse.data?.result;

        return {
            sprint,
            allTaskCount: progress?.allTaskCount,
            doneTaskCount: tasks.filter(task => task.status === 'DONE').length,
            doneStoryPoints: doneTasks
        };
    } catch (error) {
        console.error('Error fetching burndown data:', error);
        throw error;
    }
};

export const calculateBurndownData = (doneStoryPoints, sprintStartDate, sprintEndDate) => {
    const totalTasks = doneStoryPoints.length;
    const totalDays = (new Date(sprintEndDate) - new Date(sprintStartDate)) / (1000 * 60 * 60 * 24);

    const idealData = [];
    for (let i = 0; i <= totalDays; i++) {
        const remainingTasks = totalTasks - (totalTasks / totalDays) * i;
        idealData.push(remainingTasks);
    }

    const actualDataMap = doneStoryPoints.reduce((acc, task) => {
        const doneDate = new Date(task.doneDate).toLocaleDateString();
        acc[doneDate] = acc[doneDate] || 0;
        acc[doneDate] += 1;
        return acc;
    }, {});

    const actualData = [];
    let remainingTasks = totalTasks;
    for (let i = 0; i <= totalDays; i++) {
        const currentDate = new Date(new Date(sprintStartDate).getTime() + i * 24 * 60 * 60 * 1000).toLocaleDateString();
        remainingTasks -= actualDataMap[currentDate] || 0;
        actualData.push(remainingTasks);
    }

    return { idealData, actualData };
};