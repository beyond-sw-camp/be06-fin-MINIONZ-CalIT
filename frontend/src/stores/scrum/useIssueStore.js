import { ref } from "vue";
import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from 'pinia';

export const useIssueStore = defineStore('issueStore', () => {
    const issues = ref([]);

    const addIssue = async ({ workspaceId, issueName, issueDescription, issueManager, issueAssignee, issueReviewer, startDate, endDate }) => {
        try {
            const response = await axiosInstance.post(`/api/issue/${workspaceId}`, {
                title: issueName,
                contents: issueDescription,
                managerId: issueManager,
                assignees: issueAssignee,
                reviewers: issueReviewer,
                startDate: startDate,
                endDate: endDate
            });
            issues.value.push(response.data.result);
        } catch (error) {
            console.error('Error adding issue:', error);
        }
    };

    const getIssueList = async (workspaceId) => {
        try {
            const response = await axiosInstance.get(`/api/issue/${workspaceId}/list`);
            issues.value = response.data.result;
        } catch (error) {
            console.error('Error fetching issues:', error);
        }
    };

    const getIssue = async ({workspaceId, issueId}) => {
        try {
            const response = await axiosInstance.get(`/api/issue/${workspaceId}/detail/${issueId}`);
            issues.value = response.data.result;
        } catch (error) {
            console.error('Error fetching issues:', error);
        }
    };

    return {
        issues,
        addIssue,
        getIssue,
        getIssueList
    };
});