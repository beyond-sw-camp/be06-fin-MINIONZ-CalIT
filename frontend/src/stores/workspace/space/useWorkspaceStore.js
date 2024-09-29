import {ref} from 'vue';
import axios from "axios";
import {setPersona} from "@/utils/personaUtils";
import {defineStore} from "pinia";

export const workspaceStore = defineStore('workspaceStore', () => {
    const workspace = ref([]);
    const workspaceId = ref(null);
    const workspaceName = ref('');
    const persona = ref(setPersona(null));

    // POST 워크스페이스 생성 /api/workspaces
     const addWorkspace = async({workspaceName, participants, persona}) => {
        try {
            const response = await axios.post('/api/workspace', { workspaceName, participants, persona });
            this.workspace = response.data;
        } catch (error) {
            console.error('Failed to add workspace', error);
        }
    }

    // GET 워크스페이스 조회 /api/workspaces/all
     const getAllWorkspace = async() => {
        try {
            const response = await axios.get('/api/workspace/all');
            this.workspace = response.data.map(ws => ({
                ...ws,
                persona: setPersona(ws.persona)
            }));
        } catch (error) {
            console.error('Failed to fetch workspace', error);
        }
    }

    // PATCH 워크스페이스 수정 api/workspace/:id
     const updateWorkspace = async({workspaceId, workspaceName, participants}) => {
        try {
            const response = await axios.patch(`/api/workspace/${workspaceId}`, { workspaceId, workspaceName, participants });
            this.workspace = response.data;
        } catch (error) {
            console.error('Failed to update workspace', error);
        }
    }

    // DELETE 워크스페이스 삭제 api/workspace/:id
     const deleteWorkspace = async(workspaceId) => {
        try {
            const response = await axios.delete(`/api/workspace/${workspaceId}`);
            this.workspace = response.data;
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
            this.workspaceName = workspace.workspaceName;
            this.persona = setPersona(workspace.persona);
        } else {
            console.error(`Workspace with id ${id} not found`);
            this.workspaceName = '';
            this.persona = null;
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