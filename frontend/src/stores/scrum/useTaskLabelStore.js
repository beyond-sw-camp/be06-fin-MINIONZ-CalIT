import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { labelColorPalette } from '@/utils/labelUtils';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useTaskLabelStore = defineStore('labelStore', () => {
  const labels = ref([]);
  const labelColors = ref(labelColorPalette);

  const addTaskLabel = async ({ workspaceId, labelName, description, color }) => {
    try {
      const response = await axiosInstance.post(
          `/api/label/${workspaceId}/task`,
          { workspaceId, labelName, description, color }
      );

      if (response.data.success) {
        labels.value.push(response.data.result);
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error adding label:', error);
      }
    }
  };

  const getTaskLabels = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(`/api/label/${workspaceId}/task`);

      if (response.data.success) {
        labels.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error fetching labels:', error);
      }
    }
  };

  const updateLabel = async ({ workspaceId, taskLabelId, labelName, description }) => {
    try {
      const response = await axiosInstance.patch(
          `/api/label/${workspaceId}/task`,
          { taskLabelId, labelName, description }
      );

      if (response.data.success) {
        labels.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error updating label:', error);
      }
    }
  };

  const deleteLabel = async (workspaceId, taskLabelId) => {
    try {
      await axiosInstance.delete(`/api/label/${workspaceId}/task/${taskLabelId}`);

      labels.value = labels.value.filter((label) => label.id !== taskLabelId);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error deleting label:', error);
      }
    }
  };

  return {
    labels,
    labelColors,
    addTaskLabel,
    getTaskLabels,
    updateLabel,
    deleteLabel,
  };
});