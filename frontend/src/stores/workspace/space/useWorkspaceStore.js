import { ref } from 'vue';
import axios from "axios";
import { setPersona } from "@/utils/personaUtils";
import { defineStore } from "pinia";
// import { workspaceData } from "@/static/workspaceData";

export const useWorkspaceStore = defineStore('workspaceStore', () => {

    const workspace = ref([]);
    const workspaceId = ref(null);
    const workspaceName = ref('');
    const persona = ref(setPersona(null));

    // POST 워크스페이스 생성 /api/workspaces
    const addWorkspace = async({workspaceName, participants, persona}) => {
        try {
            const response = await axios.post('/api/workspace', { workspaceName, participants, persona });
            workspace.value = response.data;
            return response.data;
        } catch (error) {
            console.error('Failed to add workspace', error);
            throw error;
        }
    }

    // GET 워크스페이스 리스트 조회 /api/workspaces/all
    const getAllWorkspace = async() => {
        try {
            const response = await axios.get('/api/workspace/my/all');
            workspace.value = response.data.map(ws => ({
                ...ws,
                persona: setPersona(ws.persona)
            }));
            return workspace.value;
        } catch (error) {
            console.error('Failed to fetch workspace', error);
            return [];
        }
    }

    // PATCH 워크스페이스 수정 api/workspace/:id
    const updateWorkspace = async({workspaceId, workspaceName, participants}) => {
        try {
            const response = await axios.patch(`/api/workspace/${workspaceId}`, { workspaceId, workspaceName, participants });
            workspace.value = response.data;
        } catch (error) {
            console.error('Failed to update workspace', error);
        }
    }

    // DELETE 워크스페이스 삭제 api/workspace/:id
    const deleteWorkspace = async(workspaceId) => {
        try {
            const response = await axios.delete(`/api/workspace/${workspaceId}`);
            workspace.value = response.data;
        } catch (error) {
            console.error('Failed to delete workspace', error);
        }
    }

    // api 아님 라우터에서 받은 정보 셋팅해주는 함수
    const setWorkspaceId = async(id) => {
        console.log(`Setting workspaceId to ${id}`);
        const allWorkspaces = await getAllWorkspace();
        const workspace = allWorkspaces.find(ws => ws.id === id);
        if (workspace) {
            workspaceName.value = workspace.workspaceName;
            persona.value = setPersona(workspace.persona);
        } else {
            console.error(`Workspace with id ${id} not found`);
            workspaceName.value = '';
            persona.value = null;
        }
    }

    return {
        workspace,
        workspaceId,
        workspaceName,
        persona,
        setWorkspaceId,
        addWorkspace,
        getAllWorkspace,
        updateWorkspace,
        deleteWorkspace
    };
});
