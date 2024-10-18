import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';

export const useTaskStore = defineStore('taskStore', () => {
  const taskData = ref([]);
  const taskList = ref([]);

  const addTask = async (data, sprintId) => {
    try {
      // sprintId와 함께 데이터를 서버에 전송
      const response = await axiosInstance.post(`/api/task/${sprintId}`, data);

      console.log(response.data.result);
      // taskData.value.push(response.data.result); // 필요시 추가
    } catch (error) {
      console.error('Error adding task:', error);
    }
  };

  const updateTaskStatus = async ({ sprintId, taskId, status }) => {
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
      const response = await axiosInstance.get(
        `/api/task/${sprintId}/all/status`
      );
      taskList.value = response.data.result;
      return response.data.result;
    } catch (error) {
      console.error('Error getting task list:', error);
    }
  };
  const getWorkspaceTaskList = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(`/api/task/${workspaceId}/workspaceall`);
      console.log('API 응답:', response); // 응답 전체 확인
      return response.data.result; // 응답 데이터 반환
    } catch (error) {
      console.error('Error getting task list:', error);
      return []; // 오류 시 빈 배열 반환
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

      return response.data.result;
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
    taskList,
    getWorkspaceTaskList
  };
});
