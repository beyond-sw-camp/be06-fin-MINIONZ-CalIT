import { ref } from "vue";
import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from 'pinia';

export const useIssueStore = defineStore('issueStore', () => {
    const issues = ref([]);
    const getIssues = async () => {
        try {
            const response = await axiosInstance.get('/api/issues');
            issues.value = response.data.result;
        } catch (error) {
            console.error('Error fetching issues:', error);
        }
    };
    const addIssue = async (issue) => {
        try {
            const response = await axiosInstance.post('/api/issues', issue);
            issues.value.push(response.data.result);
        } catch (error) {
            console.error('Error adding issue:', error);
        }
    };

    // const updateIssue = async (index, updatedIssue) => {
    //     try {
    //         const response = await axiosInstance.put(`/api/issues/${updatedIssue.id}`, updatedIssue);
    //         issues.value[index] = response.data;
    //     } catch (error) {
    //         console.error('Error updating issue:', error);
    //     }
    // };
    //
    // const deleteIssue = async (index) => {
    //     try {
    //         await axiosInstance.delete(`/api/issues/${issues.value[index].id}`);
    //         issues.value.splice(index, 1);
    //     } catch (error) {
    //         console.error('Error deleting issue:', error);
    //     }
    // };

    return {
        issues,
        getIssues,
        addIssue,
        // updateIssue,
        // deleteIssue
    };
});