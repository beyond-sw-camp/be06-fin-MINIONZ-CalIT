import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { labelColorPalette } from '@/utils/labelUtils';

export const useTaskLabelStore = defineStore('labelStore', () => {
  const labels = ref([]);
  const labelColors = ref(labelColorPalette);

  const addTaskLabel = async ({
    workspaceId,
    labelName,
    description,
    color,
  }) => {
    try {
      const response = await axiosInstance.post(
        `/api/label/${workspaceId}/task`,
        { workspaceId, labelName, description, color }
      );
      labels.value.push(response.data.result);
    } catch (error) {
      console.error('Error adding label:', error);
    }
  };

  const getTaskLabel = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/label/${workspaceId}/task`
      );
      labels.value = response.data.result;
    } catch (error) {
      console.error('Error fetching labels:', error);
    }
  };

  const updateLabel = async ({
    workspaceId,
    taskLabelId,
    labelName,
    description,
  }) => {
    try {
      const response = await axiosInstance.patch(
        `/api/label/${workspaceId}/sprint`,
        {
          taskLabelId,
          labelName,
          description,
        }
      );
      labels.value = response.data.result;
    } catch (error) {
      console.error('Error updating label:', error);
    }
  };

  const deleteLabel = async (workspaceId, taskLabelId) => {
    try {
      await axiosInstance.delete(
        `/api/label/${workspaceId}/task/${taskLabelId}`
      );
      labels.value = labels.value.filter((label) => label.id !== taskLabelId);
    } catch (error) {
      console.error('Error deleting label:', error);
    }
  };

  return {
    labels,
    labelColors,
    addTaskLabel,
    getTaskLabel,
    updateLabel,
    deleteLabel,
  };
});
