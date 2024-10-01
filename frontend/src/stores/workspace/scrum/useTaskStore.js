import { ref } from 'vue';
import axios from 'axios';
import { defineStore} from "pinia";
// import { taskData } from '@/static/taskData';
// import { getTaskCountBackgroundColor, getTaskCountColor } from '@/utils/taskUtils';

//axios ver
export const useTaskStore = defineStore('taskStore', () => {
    const taskData = ref([]);
    const addTask = async ({sprintId, startDate, endDate, title, contents, difficulty, priority, labels, participants}) => {
        try {
            const response = await axios.post(`api/task/${sprintId}`, {startDate, endDate, title, contents, difficulty, priority, labels, participants});
            taskData.value.push(response.data);
        } catch (error) {
            console.error('Error adding task:', error);
        }
    };

    const getTask = async ({sprintId, taskId}) => {
        try {
            const response = await axios.get(`api/task/${sprintId}/${taskId}`);
            return response.data;
        } catch (error) {
            console.error('Error getting task:', error);
        }
    };

    const getTaskList = async (sprintId) => {
        try {
            const response = await axios.get(`/api/task/${sprintId}/all`);
            taskData.value = response.data;
        } catch (error) {
            console.error('Error getting task list:', error);
        }
    };

    const updateTask = async ({taskId, title, contents, difficulty, priority, labels, participants}) => {
        try {
            const response = await axios.put(`api/task/${taskId}`, {taskId, title, contents, difficulty, priority, labels, participants});
            taskData.value = response.data;
        } catch (error) {
            console.error('Error updating task:', error);
        }
    };

    const updateTaskStatus = async ({sprintId, taskId}) => {
        try {
            const response = await axios.put(`api/task/${sprintId}/status/${taskId}`, {taskId, status});
            taskData.value = response.data;
        } catch (error) {
            console.error('Error updating task status:', error);
        }
    };

    const deleteTask = async ({sprintId, taskId}) => {
        try {
            await axios.delete(`api/task/${sprintId}/${taskId}`);
            taskData.value = taskData.value.filter(task => task.id !== taskId);
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    }

    const getAllTask = async (workspaceId) => {
        try {
            const response = await axios.get(`/api/sprint/all/${workspaceId}`);
            taskData.value = response.data;
        } catch (error) {
            console.error('Error getting task list:', error);
        }
    };

    // api 안씀
    const getTaskCount = () => {
        return taskData.value.length
    }

    return {
        addTask,
        getTask,
        getTaskList,
        getTaskCount,
        updateTask,
        updateTaskStatus,
        deleteTask,
        getAllTask,
    }
})