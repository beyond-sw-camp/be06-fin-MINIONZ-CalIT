import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { useAlarmStore } from '@/stores/alarm/useAlarmStore';

export const useWorkspaceStore = defineStore('workspaceStore', () => {
  const workspace = ref([]);
  const workspaceId = ref(null);
  const workspaceName = ref('');
  const nowWorkspace = ref(null);

  // POST 워크스페이스 생성 /api/workspaces
  const addWorkspace = async ({ workspaceName, participants, avatar }) => {
    try {
      console.log('Sending request to API with:', { workspaceName, participants, avatar });
      const response = await axiosInstance.post('/api/workspace', {
        workspaceName,
        participants,
        avatar,
      });
      console.log('API response:', response.data);
      workspace.value = response.data.result;
      return response.data;
    } catch (error) {
      console.error('Failed to add workspace', error);
      throw error;
    }
  };

  // GET 워크스페이스 리스트 조회 /api/workspaces/all
  const getAllWorkspace = async () => {
    try {
      const response = await axiosInstance.get('/api/workspace/my/all', {
        withCredentials: true,
      });
      workspace.value = response.data.result;
      return response.data.result;
    } catch (error) {
      console.error('Failed to fetch workspace', error);
      return [];
    }
  };

  // PATCH 워크스페이스 수정 api/workspace/:id
  const updateWorkspace = async ({ workspaceId, workspaceName, participants }) => {
    try {
      const response = await axiosInstance.patch(`/api/workspace/${workspaceId}`, {
        workspaceId,
        workspaceName,
        participants,
      });
      workspace.value = response.data.result;
      return response.data.result; // 수정된 데이터 반환
    } catch (error) {
      console.error('Failed to update workspace', error);
      throw error;
    }
  };

  // DELETE 워크스페이스 삭제 api/workspace/:id
  const deleteWorkspace = async (workspaceId) => {
    try {
      const response = await axiosInstance.delete(`/api/workspace/${workspaceId}`);
      workspace.value = response.data.result;

      if (workspaceId === nowWorkspace.value?.workspaceId) {
        nowWorkspace.value = null;
        workspaceId.value = null;
        workspaceName.value = '';
      }

      return response.data;
    } catch (error) {
      console.error('Failed to delete workspace', error);
      throw error;
    }
  };

  // 라우터에서 받은 워크스페이스 ID 설정 (API 아님)
  const setWorkspaceId = async (id) => {
    if (!workspace.value.length) {
      await getAllWorkspace();
    }
    workspaceId.value = id;
    const selectedWorkspace = workspace.value.find((ws) => ws.workspaceId === id);
    if (selectedWorkspace) {
      workspaceName.value = selectedWorkspace.workspaceName;
    } else {
      workspaceName.value = '';
    }
  };

  // 현재 워크스페이스 설정
  const setNowWorkspace = (workspace) => {
    nowWorkspace.value = workspace;
  };

  // 워크스페이스 수락
  const acceptWorkspace = async (workspaceId) => {
    try {
      const response = await axiosInstance.patch(`/api/workspace/accept/${workspaceId}`);
      workspace.value = response.data;
      return response.data;
    } catch (error) {
      console.error('Failed to accept workspace', error);
      throw error;
    }
  };

  // 워크스페이스 거절
  const rejectWorkspace = async (workspaceId) => {
    const alarmStore = useAlarmStore();
    try {
      const response = await axiosInstance.delete(`/api/workspace/reject/${workspaceId}`);
      workspace.value = response.data;
      await alarmStore.deleteAlarm(workspaceId);
      return response.data;
    } catch (error) {
      console.error('Failed to reject workspace', error);
      throw error;
    }
  };

  return {
    workspace,
    workspaceId,
    workspaceName,
    addWorkspace,
    getAllWorkspace,
    updateWorkspace,
    deleteWorkspace,
    setWorkspaceId,
    acceptWorkspace,
    rejectWorkspace,
    setNowWorkspace,
  };
});