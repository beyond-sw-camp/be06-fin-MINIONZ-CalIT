import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { useRoute } from 'vue-router';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useSprintStore = defineStore('sprintStore', () => {
  const sprint = ref([]);
  const sprints = ref([]);
  const sprintId = ref(null);
  const nowSprintId = ref(null);

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

      if (response.data.success) {
        sprints.value.push(response.data.result);
        notyf.success('스프린트가 성공적으로 추가되습니다.');
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        console.error('Error adding label:', error);
        notyf.error('스프린트 추가에 실패하였습니다.');
      }
    }
  };

  const getSprint = async () => {
    const workspaceId = route.params.workspaceId;
    const sprintId = route.params.sprintId;

    try {
      const response = await axiosInstance.get(
        `/api/sprint/${workspaceId}/${sprintId}`
      );

      if (response.data.success) {
        sprint.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }

      console.log('응답 코드', response.status);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.log('Error getting Sprint', error);
      }
    }
  };

  const getSprintList = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/sprint/all/${workspaceId}`
      );

      if (response.data.success) {
        sprints.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.log('Error getting Sprint List', error);
      }
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

      if (response.data.success) {
        sprints.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.log('Error updating Sprint', error);
      }
    }
  };

  const updateSprintState = async (sprintId, status) => {
    try {
      const response = await axiosInstance.patch(
        `/api/sprint/${sprintId}/status`,
        status
      );

      if (response.data.success) {
        sprints.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.log('Error updating Sprint State', error);
      }
    }
  };

  const deleteSprint = async (sprintId) => {
    try {
      await axiosInstance.delete(`/api/sprint/${sprintId}`);
      sprints.value = sprints.value.filter((sprint) => sprint.id !== sprintId);
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error deleting sprint:', error);
      }
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

  const setNowSprintId = (id) => {
    nowSprintId.value = id;
  };

  return {
    sprint,
    sprints,
    sprintId,
    nowSprintId,
    addSprint,
    getSprint,
    getSprintList,
    updateSprint,
    updateSprintState,
    deleteSprint,
    setSprintId,
    setNowSprintId,
  };
});
