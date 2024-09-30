import { ref } from 'vue';
import axios from 'axios';
import { defineStore } from 'pinia';

export const useSprintStore = defineStore('sprintStore', () => {
    const sprints = ref([]);

    const addSprint = async({workspaceId, sprintTitle, sprintContents, labels, participants, startData, endDate}) => {
        try{
            const response = await axios.post(`/api/sprint/${workspaceId}`, {workspaceId, sprintTitle, sprintContents, labels, participants, startData, endDate});
            sprints.value.push(response.data);
        }
        catch (error){
            console.error('Error adding label:', error);
        }
    }

    const getSprint = async(sprintId) => {
        try {
            const response  = await axios.post(`/api/sprint/${sprintId}`);
            sprints.value = response.data;
        }
        catch (error) {
            console.log('Error getting Sprint', error)
        }
    }

    const getSprintList = async(workspaceId) => {
        try{
            const response = await axios.get(`/api/sprint/${workspaceId}`)
            sprints.value = response.data;
        }
        catch (error) {
            console.log('Error getting Sprint List', error);
        }
    }

    const updateSprint = async ({sprintId, sprintTitle, sprintContents, labelId}) => {
        try {
            const response = await axios.put(`/api/sprint`, {sprintId, sprintTitle, sprintContents, labelId});
            sprints.value = response.data;
        }
        catch (error) {
            console.log('Error updating Sprint', error);
        }
    }

    const updateSprintState = async(status) => {
        try {
            const response = axios.put(`/api/sprint/status`, status);
            sprints.value = response.data;
        }
        catch (error) {
            console.log('Error updating Sprint State', error);
        }
    }


    const deleteSprint = async(sprintId) => {
        try{
            await axios.delete(`/api/sprint/${sprintId}`);
            sprints.value = sprints.value.filter(sprint => sprint.id !== sprintId);
        }
        catch (error) {
            console.error('Error deleting sprint:', error);
        }
    }

    return {
        sprints,
        addSprint,
        getSprint,
        getSprintList,
        updateSprint,
        updateSprintState,
        deleteSprint
    }
});