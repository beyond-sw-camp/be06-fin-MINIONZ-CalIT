import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { useRoute } from 'vue-router';

export const useSprintStore = defineStore('sprintStore', () => {
  const sprint = ref([]);
  const sprints = ref([]);
  const sprintId = ref(null);

  const route = useRoute();

  const addSprint = async ({
    workspaceId,
    sprintTitle,
    sprintContents,
    labels,
    participants,
    startDate,
    endDate,
  }) => {
    try {
      const response = await axiosInstance.post(`/api/sprint/${workspaceId}`, {
        workspaceId,
        sprintTitle,
        sprintContents,
        labels,
        participants,
        startDate,
        endDate,
      });
      sprints.value.push(response.data.result);
    } catch (error) {
      console.error('Error adding label:', error);
    }
  };

  const getSprint = async () => {
    const workspaceId = route.params.workspaceId;
    const sprintId = route.params.sprintId;

    try {
      const response = await axiosInstance.get(
        `/api/sprint/${workspaceId}/${sprintId}`
      );
      sprint.value = response.data.result;
    } catch (error) {
      console.log('Error getting Sprint', error);
    }
  };

  const getSprintList = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/sprint/all/${workspaceId}`
      );
      sprints.value = response.data.result;
    } catch (error) {
      console.log('Error getting Sprint List', error);
    }
  };

  const updateSprint = async ({
    sprintId,
    sprintTitle,
    sprintContents,
    labelId,
  }) => {
    try {
      const response = await axiosInstance.patch(`/api/sprint/${sprintId}`, {
        sprintId,
        sprintTitle,
        sprintContents,
        labelId,
      });
      sprints.value = response.data.result;
    } catch (error) {
      console.log('Error updating Sprint', error);
    }
  };

  const updateSprintState = async (sprintId, status) => {
    try {
      const response = await axiosInstance.patch(
        `/api/sprint/${sprintId}/status`,
        status
      );
      sprints.value = response.data.result;
    } catch (error) {
      console.log('Error updating Sprint State', error);
    }
  };

  const deleteSprint = async (sprintId) => {
    try {
      await axiosInstance.delete(`/api/sprint/${sprintId}`);
      sprints.value = sprints.value.filter((sprint) => sprint.id !== sprintId);
    } catch (error) {
      console.error('Error deleting sprint:', error);
    }
  };

  const setSprintId = async (id) => {
    await getSprintList();
    sprintId.value = id;
    const selectedSprint = sprints.value.find(
      (sprint) => sprint.sprintId === id
    );
    if (selectedSprint) {
      sprint.value = selectedSprint;
    } else {
      sprint.value = null;
    }
  };

  return {
    sprint,
    sprints,
    sprintId,
    addSprint,
    getSprint,
    getSprintList,
    updateSprint,
    updateSprintState,
    deleteSprint,
    setSprintId,
  };
});
