import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { labelColorPalette } from '@/utils/labelUtils';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useSprintLabelStore = defineStore('labelStore', () => {
  const labels = ref([]);
  const labelColors = ref(labelColorPalette);

  const addSprintLabel = async ({
    workspaceId,
    labelName,
    description,
    color,
  }) => {
    try {
      const response = await axiosInstance.post(
        `/api/label/${workspaceId}/sprint`,
        { labelName, description, color }
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

  const getSprintLabel = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/label/${workspaceId}/sprint`
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
        console.error('Error fetching labels:', error);
      }
    }
  };

  const updateLabel = async ({
    workspaceId,
    sprintLabelId,
    labelName,
    description,
  }) => {
    try {
      const response = await axiosInstance.patch(`/api/label/${workspaceId}`, {
        sprintLabelId,
        labelName,
        description,
      });

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

  const deleteLabel = async (workspaceId, sprintLabelId) => {
    try {
      await axiosInstance.delete(`/api/label/${workspaceId}/${sprintLabelId}`);
      labels.value = labels.value.filter((label) => label.id !== sprintLabelId);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error deleting label:', error);
      }
    }
  };

  const addLabel = (label) => {
    labels.value.push(label);
  };

  return {
    labels,
    labelColors,
    addSprintLabel,
    getSprintLabel,
    updateLabel,
    deleteLabel,
    addLabel,
  };
});
