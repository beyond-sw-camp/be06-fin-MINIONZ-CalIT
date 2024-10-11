import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { useRoute } from 'vue-router';

export const useTaskStore = defineStore('taskStore', () => {
  const taskData = ref([]);
  const route = useRoute();

  const addTask = async ({
    startDate,
    endDate,
    title,
    contents,
    difficulty,
    priority,
    labels,
    participants,
  }) => {
    try {
      const sprintId = route.params.sprintId;
      const response = await axiosInstance.post(`/api/task/${sprintId}`, {
        startDate,
        endDate,
        title,
        contents,
        difficulty,
        priority,
        labels,
        participants,
      });
      taskData.value.push(response.data.result);
    } catch (error) {
      console.error('Error adding task:', error);
    }
  };

  const updateTaskStatus = async ({ sprintId, taskId }) => {
    try {
      const response = await axiosInstance.put(
        `/api/task/${sprintId}/status/${taskId}`,
        { taskId, status }
      );
      taskData.value = response.data.result;
    } catch (error) {
      console.error('Error updating task status:', error);
    }
  };

  const getTask = async ({ sprintId, taskId }) => {
    try {
      const response = await axiosInstance.get(
        `/api/task/${sprintId}/${taskId}`
      );
      return response.data.result.result;
    } catch (error) {
      console.error('Error getting task:', error);
    }
  };

  const getTaskList = async (sprintId) => {
    try {
      const response = await axiosInstance.get(`/api/task/${sprintId}/all`);
      taskData.value = response.data.result;
    } catch (error) {
      console.error('Error getting task list:', error);
    }
  };

  const updateTask = async ({
    taskId,
    title,
    contents,
    difficulty,
    priority,
    labels,
    participants,
  }) => {
    try {
      const response = await axiosInstance.put(`api/task/${taskId}`, {
        taskId,
        title,
        contents,
        difficulty,
        priority,
        labels,
        participants,
      });
      taskData.value = response.data.result;
    } catch (error) {
      console.error('Error updating task:', error);
    }
  };

  const deleteTask = async ({ sprintId, taskId }) => {
    try {
      await axiosInstance.delete(`api/task/${sprintId}/${taskId}`);
      taskData.value = taskData.value.filter((task) => task.id !== taskId);
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  const getMyTask = async () => {
    try {
      const response = await axiosInstance.get(`/api/sprint/my/all`);
      taskData.value = response.data.result;
    } catch (error) {
      console.error('Error getting task list:', error);
    }
  };

  // api 안씀
  const getTaskCount = () => {
    return taskData.value.length;
  };

  return {
    addTask,
    getTask,
    getTaskList,
    getTaskCount,
    updateTask,
    updateTaskStatus,
    deleteTask,
    getMyTask,
  };
});
