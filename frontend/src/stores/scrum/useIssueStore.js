import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';

export const useIssueStore = defineStore('issueStore', () => {
  const issues = ref([]);

  const addIssue = async (workspaceId, data) => {
    try {
      const response = await axiosInstance.post(
        `/api/issue/${workspaceId}`,
        data
      );
      issues.value.push(response.data.result);
    } catch (error) {
      console.error('Error adding issue:', error);
    }
  };

  const getIssueList = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/issue/${workspaceId}/list`
      );
      issues.value = response.data.result;
    } catch (error) {
      console.error('Error fetching issues:', error);
    }
  };

  const getIssue = async ({ workspaceId, issueId }) => {
    try {
      const response = await axiosInstance.get(
        `/api/issue/${workspaceId}/detail/${issueId}`
      );
      issues.value = response.data.result;
    } catch (error) {
      console.error('Error fetching issues:', error);
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
    addIssue,
    getIssue,
    getIssueList,
  };
});
