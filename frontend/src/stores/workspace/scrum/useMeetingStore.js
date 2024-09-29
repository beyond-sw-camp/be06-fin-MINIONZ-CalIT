import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';
// import { meetingData } from '@/static/meetingData';

export const useMeetingStore = defineStore('meetingStore', () => {
    const meetings =  ref([]);

    const addMeeting = async ({sprintId, startDate, endDate, meetingTitle, meetingContents, participants }) => {
        try {
            const response = await axios.post(`api/${sprintId}/meeting`, {startDate, endDate, meetingTitle, meetingContents, participants});
            meetings.value.push(response.data);
        }
        catch (error) {
            console.error(error);
        }
    }

    const getMeeting = async ({workspaceId, meetingId}) => {
        try {
            const response = await axios.get(`api/meeting/${workspaceId}/${meetingId}`);
            return response.data;
        }
        catch (error) {
            console.error(error);
        }
    }

    const getMeetingList = async ({workspaceId, meetingId}) => {
        try {
            const response = await axios.get(`api/meeting/${workspaceId}/${meetingId}`);
            meetings.value = response.data;
        }
        catch (error) {
            console.error(error);
        }
    }

    const updateMeeting = async ({ meetingId, meetingTitle, meetingContents }) => {
        try {
            const response = await axios.put(`api/meeting/${meetingId}`, {meetingId, meetingTitle, meetingContents});
            return response.data;
        }
        catch (error) {
            console.error(error);
        }
    }

    const deleteMeeting = async ({ meetingId }) => {
        try {
            const response = await axios.delete(`api/meeting/${meetingId}`);
            return response.data;
        }
        catch (error) {
            console.error(error);
        }
    }

    const alertMeeting = async ({ sprintId, startDate, endDate, meetingTitle, meetingContents, participants }) => {
        try {
            const response = await axios.get(`api/${sprintId}/meeting`, {startDate, endDate, meetingTitle, meetingContents, participants});
            return response.data;
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