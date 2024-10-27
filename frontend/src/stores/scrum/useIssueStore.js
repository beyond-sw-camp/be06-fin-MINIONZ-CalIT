import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useIssueStore = defineStore('issueStore', () => {
  const issues = ref([]);

  const addIssue = async (workspaceId, data) => {
    try {
      const response = await axiosInstance.post(
        `/api/issue/${workspaceId}`,
        data
      );

      if (response.data.success) {
        issues.value.push(response.data.result);
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error adding issue:', error);
      }
    }
  };

  const getIssueList = async (workspaceId) => {
    try {
      const response = await axiosInstance.get(
        `/api/issue/${workspaceId}/list`
      );

      if (response.data.success) {
        issues.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error fetching issues:', error);
      }
    }
  };

  const getIssue = async ({ workspaceId, issueId }) => {
    try {
      const response = await axiosInstance.get(
        `/api/issue/${workspaceId}/detail/${issueId}`
      );

      if (response.data.success) {
        issues.value = response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Error fetching issues:', error);
      }
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
