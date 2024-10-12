import { ref } from 'vue';
import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from 'pinia';

export const useMeetingStore = defineStore('meetingStore', () => {
    const meetings =  ref([]);
    const meetingId = ref(null);

<<<<<<< HEAD
    const addMeeting = async ({workspaceId, startDate, endDate, meetingTitle, meetingContents, participants }) => {
        try {
            const response = await axiosInstance.post(`/api/${workspaceId}/meeting`, {startDate, endDate, meetingTitle, meetingContents, participants});
=======
    const addMeeting = async (data, sprintId) => {
        try {

            const response = await axiosInstance.post(`/api/meeting/${sprintId}`, data);
>>>>>>> 6901ea1cc599d34edd505880ca216b8eb9a914bd
            meetings.value.push(response.data.result);
        }
        catch (error) {
            console.error(error);
        }
    }

    const getMeeting = async ({workspaceId, meetingId}) => {
        try {
            const response = await axiosInstance.get(`/api/meeting/${workspaceId}/${meetingId}`);
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const getMeetingList = async (workspaceId) => {
        try {
<<<<<<< HEAD
            const response = await axiosInstance.get(`/api/meeting/${workspaceId}/search-all`);
            meetings.value = response.data.result;
=======
            const response = await axiosInstance.get(`/api/meeting/${workspaceId}/search-all?page=0&size=10`);
            meetings.value = response.data.result.content;
            return response.data.result.content;
>>>>>>> 6901ea1cc599d34edd505880ca216b8eb9a914bd
        }
        catch (error) {
            console.error(error);
        }
    }

    const updateMeeting = async ({ meetingId, meetingTitle, meetingContents }) => {
        try {
            const response = await axiosInstance.put(`/api/meeting/${meetingId}`, {meetingId, meetingTitle, meetingContents});
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const deleteMeeting = async ({ meetingId }) => {
        try {
            const response = await axiosInstance.delete(`/api/meeting/${meetingId}`);
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const alertMeeting = async ({ sprintId, startDate, endDate, meetingTitle, meetingContents, participants }) => {
        try {
            const response = await axiosInstance.get(`/api/${sprintId}/meeting`, {startDate, endDate, meetingTitle, meetingContents, participants});
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const setMeetingId = (id) => {
        meetingId.value = id
    }

    return {
        meetings,
        meetingId,
        addMeeting,
        getMeeting,
        getMeetingList,
        updateMeeting,
        deleteMeeting,
        alertMeeting,
        setMeetingId
    }
})