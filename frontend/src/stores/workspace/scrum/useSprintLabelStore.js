import {ref} from "vue";
import axios from "axios";
import { defineStore } from 'pinia';
import { labelColorPalette } from '@/utils/labelUtils';
// import { labelData } from '@/static/labelData';

export const useSprintLabelStore = defineStore('labelStore', () => {
    const labels = ref([]);
    const labelColors = ref(labelColorPalette);

    const addSprintLabel = async ({workspaceId, labelName, description, color}) => {
        try {
            const response = await axios.get('/api/label/sprint', {workspaceId, labelName, description, color});
            labels.value.push(response.data);
        }
        catch (error) {
            console.error('Error adding label:', error);
        }
    }

    const getSprintLabel = async (workspaceId) => {
        try {
            const response = await axios.post(`/api/label/sprint?id=${workspaceId}`);
            labels.value = response.data;
        }
        catch (error) {
            console.error('Error fetching labels:', error);
        }
    };

    const updateLabel = async ({sprintLabelId, labelName, description}) => {
        try {
            const response = await axios.put(`/api/label?id=${sprintLabelId}`, {sprintLabelId, labelName, description});
            labels.value = response.data;
        }
        catch (error) {
            console.error('Error updating label:', error);
        }
    };

    const deleteLabel = async (sprintLabelId) => {
        try {
            await axios.delete(`/api/label?id=${sprintLabelId}`);
            labels.value = labels.value.filter(label => label.id !== sprintLabelId);
        }
        catch (error) {
            console.error('Error deleting label:', error);
        }
    };

    return {
        labels,
        labelColors,
        addSprintLabel,
        getSprintLabel,
        updateLabel,
        deleteLabel
    }
});