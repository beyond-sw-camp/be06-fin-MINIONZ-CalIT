import { reactive } from 'vue';
import axios from "axios";
import {setPersona} from "@/utils/personaUtils";

export const workspaceStore = reactive({
    workspace: [],
    workspaceId: null,
    workspaceName: '',
    persona: setPersona(),

    // 라우터에서 받은 정보 셋팅해주는 함수
    async setWorkspaceId(id) {
        console.log(`Setting workspaceId to ${id}`);
        const allWorkspaces = await this.getAllWorkspace();
        const workspace = allWorkspaces.find(ws => ws.id === id);
        if (workspace) {
            this.workspaceName = workspace.workspaceName;
            this.persona = setPersona(workspace.persona);
        } else {
            console.error(`Workspace with id ${id} not found`);
            this.workspaceName = '';
            this.persona = null;
        }
    },

    // POST 워크스페이스 생성 /api/workspaces
    async addWorkspace(workspaceName, participants) {
        try {
            const response = await axios.post('/api/workspace', { workspaceName, participants });
            this.workspace = response.data;
        } catch (error) {
            console.error('Failed to add workspace', error);
        }
    },

    // GET 워크스페이스 조회 /api/workspaces/all
    async getAllWorkspace() {
        try {
            const response = await axios.get('/api/workspace/all');
            this.workspace = response.data.map(ws => ({
                ...ws,
                persona: setPersona(ws.persona)
            }));
        } catch (error) {
            console.error('Failed to fetch workspace', error);
        }
    },

    // PATCH 워크스페이스 수정 api/workspace/:id
    async updateWorkspace(id, workspaceName, participants) {
        try {
            const response = await axios.patch(`/api/workspace/${id}`, { workspaceName, participants });
            this.workspace = response.data;
        } catch (error) {
            console.error('Failed to update workspace', error);
        }
    },

    // DELETE 워크스페이스 삭제 api/workspace/:id
    async deleteWorkspace(id) {
        try {
            const response = await axios.delete(`/api/workspace/${id}`);
            this.workspace = response.data;
        } catch (error) {
            console.error('Failed to delete workspace', error);
        }
    }
});