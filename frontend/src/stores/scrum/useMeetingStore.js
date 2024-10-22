import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();
export const useMeetingStore = defineStore('meetingStore', () => {
  const meetings = ref([]);
  const meetingId = ref(null);
  const meetingParticipants = ref([]);

  const addMeeting = async (data, sprintId) => {
    try {
      const response = await axiosInstance.post(
        `/api/meeting/${sprintId}`,
        data
      );
      if (response.data.success) {
        meetings.value.push(response.data.result);
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  const getMeeting = async ({ workspaceId, meetingId }) => {
    try {
      const response = await axiosInstance.get(
        `/api/meeting/${workspaceId}/${meetingId}`
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
        console.error(error);
      }
    }
  };

  const getMeetingList = async ({ workspaceId, page, size }) => {
    try {
      const response = await axiosInstance.get(
        `/api/meeting/${workspaceId}/search-all?page=${page}&size=${size}`
      );
      meetings.value = response.data.result.content;
      return response.data.result;
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  const updateMeeting = async ({
    meetingId,
    meetingTitle,
    meetingContents,
  }) => {
    try {
      const response = await axiosInstance.patch(`/api/meeting/${meetingId}`, {
        meetingId,
        meetingTitle,
        meetingContents,
      });
      return response.data.result;
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  const deleteMeeting = async ({ meetingId }) => {
    try {
      const response = await axiosInstance.delete(`/api/meeting/${meetingId}`);
      return response.data.result;
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  const alertMeeting = async ({
    sprintId,
    startDate,
    endDate,
    meetingTitle,
    meetingContents,
    participants,
  }) => {
    try {
      const response = await axiosInstance.get(`/api/${sprintId}/meeting`, {
        startDate,
        endDate,
        meetingTitle,
        meetingContents,
        participants,
      });
      return response.data.result;
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error(error);
      }
    }
  };

  const setMeetingId = (id) => {
    meetingId.value = id;
  };

  return {
    meetings,
    meetingId,
    meetingParticipants,
    addMeeting,
    getMeeting,
    getMeetingList,
    updateMeeting,
    deleteMeeting,
    alertMeeting,
    setMeetingId,
  };
});
