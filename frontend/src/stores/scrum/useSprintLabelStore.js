import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { labelColorPalette } from '@/utils/labelUtils';

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
      const response = await axiosInstance.get(
        `/api/label/${workspaceId}/sprint`,
        { labelName, description, color }
      );
      labels.value.push(response.data.result);
    } catch (error) {
      console.error('Error adding label:', error);
    }
  };

  const getSprintLabel = async (workspaceId) => {
    try {
      const response = await axiosInstance.post(
        `/api/label/${workspaceId}/sprint`
      );
      labels.value = response.data.result;
    } catch (error) {
      console.error('Error fetching labels:', error);
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
      labels.value = response.data.result;
    } catch (error) {
      console.error('Error updating label:', error);
    }
  };

  const deleteLabel = async (workspaceId, sprintLabelId) => {
    try {
      await axiosInstance.delete(`/api/label/${workspaceId}/${sprintLabelId}`);
      labels.value = labels.value.filter((label) => label.id !== sprintLabelId);
    } catch (error) {
      console.error('Error deleting label:', error);
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
