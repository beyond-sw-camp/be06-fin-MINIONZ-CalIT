import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useTaskStore = defineStore('taskStore', () => {
  const taskData = ref([]);
  const taskList = ref([]);

  const addTask = async (data, sprintId) => {
    try {
      const response = await axiosInstance.post(`/api/task/${sprintId}`, data);

      if (response.data.success) {
        taskData.value.push(response.data.result);
        notyf.success('Task가 추가되었습니다.');
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('Task 추가에 실패했습니다.');
        console.error('Error adding task:', error);
      }
    }
  };

  const updateTaskStatus = async ({ sprintId, taskId, status }) => {
    try {
      const response = await axiosInstance.patch(
        `/api/task/${sprintId}/status/${taskId}`,
        { taskId, status }
      );

      if (response.data.success) {
        taskData.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error updating task status:', error);
      }
    }
  };

  const getTask = async ({ workspaceId, sprintId, taskId }) => {
    try {
      const response = await axiosInstance.get(
        `/api/task/${workspaceId}/${sprintId}/${taskId}`
      );

      if (response.data.success) {
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error getting task:', error);
      }
    }
  };

  const getTaskList = async (workspaceId, sprintId) => {
    try {
      const response = await axiosInstance.get(
        `/api/task/${workspaceId}/${sprintId}/all/status`
      );

      if (response.data.success) {
        taskList.value = response.data.result;
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error getting task list:', error);
      }
    }
  };

  // 유저 별 태스크 칸반 조회
  const getTaskListByUser = async (workspaceId, sprintId, userId) => {
    try {
      const response = await axiosInstance.get(
        `/api/task/${workspaceId}/${sprintId}/all/status/${userId}`
      );

      if (response.data.success) {
        taskList.value = response.data.result;
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error getting task list:', error);
      }
    }
  };

  const getWorkspaceTaskList = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/workspace/${workspaceId}/tasks`
      );

      if (response.data.success) {
        return response.data.result; // 응답 데이터 반환
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error getting task list:', error);
      }
      return [];
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
      const response = await axiosInstance.patch(`api/task/${taskId}`, {
        taskId,
        title,
        contents,
        difficulty,
        priority,
        labels,
        participants,
      });
      if (response.data.success) {
        taskData.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error updating task:', error);
      }
    }
  };

  const deleteTask = async ({ sprintId, taskId }) => {
    try {
      await axiosInstance.delete(`api/task/${sprintId}/${taskId}`);
      taskData.value = taskData.value.filter((task) => task.id !== taskId);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error deleting task:', error);
      }
    }
  };

  const getMyTask = async () => {
    try {
      const response = await axiosInstance.get(`/api/sprint/my/all`);

      if (response.data.success) {
        taskData.value = response.data.result;
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error getting task list:', error);
      }
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
    getWorkspaceTaskList,
    getTaskListByUser,
  };
});
