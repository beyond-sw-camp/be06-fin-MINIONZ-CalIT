import { ref } from 'vue';
import {axiosInstance} from "@/utils/axiosInstance";
import { defineStore } from 'pinia';

export const useMeetingStore = defineStore('meetingStore', () => {
    const meetings =  ref([]);

    const addMeeting = async ({sprintId, startDate, endDate, meetingTitle, meetingContents, participants }) => {
        try {
            const response = await axiosInstance.post(`api/${sprintId}/meeting`, {startDate, endDate, meetingTitle, meetingContents, participants});
            meetings.value.push(response.data.result);
        }
        catch (error) {
            console.error(error);
        }
    }

    const getMeeting = async ({workspaceId, meetingId}) => {
        try {
            const response = await axiosInstance.get(`api/meeting/${workspaceId}/${meetingId}`);
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const getMeetingList = async ({workspaceId, meetingId}) => {
        try {
            const response = await axiosInstance.get(`api/meeting/${workspaceId}/${meetingId}`);
            meetings.value = response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const updateMeeting = async ({ meetingId, meetingTitle, meetingContents }) => {
        try {
            const response = await axiosInstance.put(`api/meeting/${meetingId}`, {meetingId, meetingTitle, meetingContents});
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const deleteMeeting = async ({ meetingId }) => {
        try {
            const response = await axiosInstance.delete(`api/meeting/${meetingId}`);
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    const alertMeeting = async ({ sprintId, startDate, endDate, meetingTitle, meetingContents, participants }) => {
        try {
            const response = await axiosInstance.get(`api/${sprintId}/meeting`, {startDate, endDate, meetingTitle, meetingContents, participants});
            return response.data.result;
        }
        catch (error) {
            console.error(error);
        }
    }

    return {
        meetings,
        addMeeting,
        getMeeting,
        getMeetingList,
        updateMeeting,
        deleteMeeting,
        alertMeeting
    }
})