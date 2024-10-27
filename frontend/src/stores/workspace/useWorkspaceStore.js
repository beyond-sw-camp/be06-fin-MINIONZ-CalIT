import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { useAlarmStore } from '@/stores/alarm/useAlarmStore';
import { Notyf } from 'notyf';
import 'notyf/notyf.min.css';

const notyf = new Notyf();

export const useWorkspaceStore = defineStore('workspaceStore', () => {
  const workspace = ref([]);
  const workspaceId = ref(localStorage.getItem('workspaceId') || null);
  const workspaceName = ref('');
  const nowWorkspace = ref(null);

  // POST 워크스페이스 생성 /api/workspaces
  const addWorkspace = async ({ workspaceName, participants, avatar }) => {
    try {
      const response = await axiosInstance.post('/api/workspace', {
        workspaceName,
        participants,
        avatar,
      });

      if (response.data.success) {
        workspace.value = response.data.result;
        return response.data;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Failed to add workspace', error);
      }

      throw error;
    }
  };

  // GET 워크스페이스 리스트 조회 /api/workspaces/all
  const getAllWorkspace = async () => {
    if (workspace.value.length > 0) {
      return workspace.value; // 이미 로드된 경우, 중복 호출 방지
    }
    try {
      const response = await axiosInstance.get('/api/workspace/my/all', {
        withCredentials: true,
      });

      if (response.data.success) {
        workspace.value = response.data.result;
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Failed to fetch workspace', error);
      }

      return [];
    }
  };

  // PATCH 워크스페이스 수정 /api/workspace/:id
  const updateWorkspace = async ({
    workspaceId,
    workspaceName,
    participants,
  }) => {
    try {
      const response = await axiosInstance.patch(
        `/api/workspace/${workspaceId}`,
        {
          workspaceId,
          workspaceName,
          participants,
        }
      );

      if (response.data.success) {
        workspace.value = response.data.result;
        return response.data.result;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Failed to update workspace', error);
      }

      throw error;
    }
  };

  // DELETE 워크스페이스 삭제 /api/workspace/:id
  const deleteWorkspace = async (workspaceId) => {
    try {
      const response = await axiosInstance.delete(
        `/api/workspace/${workspaceId}`
      );
      workspace.value = response.data.result;

      // 선택한 워크스페이스가 삭제되었을 때 상태 초기화
      if (workspaceId === nowWorkspace.value?.workspaceId) {
        nowWorkspace.value = null;
        workspaceId.value = null;
        workspaceName.value = '';
        localStorage.removeItem('workspaceId');
      }

      return response.data;
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Failed to delete workspace', error);
      }

      throw error;
    }
  };

  // 라우터에서 받은 워크스페이스 ID 설정
  const setWorkspaceId = (id) => {
    const selectedWorkspace = workspace.value.find(
      (ws) => ws.workspaceId === id
    );
    if (selectedWorkspace) {
      workspaceId.value = id;
      workspaceName.value = selectedWorkspace.workspaceName;
      localStorage.setItem('workspaceId', id);
    } else {
      workspaceId.value = null;
      workspaceName.value = '';
      localStorage.removeItem('workspaceId');
    }
  };

  // 현재 워크스페이스 설정
  const setNowWorkspace = (workspace) => {
    nowWorkspace.value = workspace;
  };

  // 워크스페이스 수락
  const acceptWorkspace = async (workspaceId) => {
    try {
      const response = await axiosInstance.patch(
        `/api/workspace/accept/${workspaceId}`
      );

      if (response.data.success) {
        workspace.value = response.data;
        return response.data;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Failed to accept workspace', error);
      }

      throw error;
    }
  };

  // 워크스페이스 거절
  const rejectWorkspace = async (workspaceId) => {
    const alarmStore = useAlarmStore();
    try {
      const response = await axiosInstance.delete(
        `/api/workspace/reject/${workspaceId}`
      );

      if (response.data.success) {
        workspace.value = response.data;
        await alarmStore.deleteAlarm(workspaceId);
        return response.data;
      } else {
        notyf.error(response.data.message);
      }
    } catch (error) {
      if (error.response && error.response.status === 403) {
        notyf.error('접근 권한이 없습니다.');
      } else {
        notyf.error('알 수 없는 오류가 발생했습니다.');
        console.error('Failed to reject workspace', error);
      }

      throw error;
    }
  };

  return {
    workspace,
    workspaceId,
    workspaceName,
    nowWorkspace,
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
