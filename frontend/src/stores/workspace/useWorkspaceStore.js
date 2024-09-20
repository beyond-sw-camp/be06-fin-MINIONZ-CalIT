import { reactive } from 'vue';
import { workspaceData } from "@/static/workspaceData";
import {useRouter} from "vue-router";

export const workspaceInfo = reactive({
    workspaceId: null,
    workspaceName: '',
    setWorkspaceInfo(id) {
        this.workspaceId = id;
        this.workspaceName = this.getWorkspaceName(id);
    },
    getWorkspaceName(id) {
        const workspace = workspaceData.find(ws => ws.workspaceId === id );
        return workspace ? workspace.workspaceName : '';
    },
    setWorkspaceName(id, name) {
        const workspace = workspaceData.find(ws => ws.workspaceId === id);
        if (workspace) {
            workspace.workspaceName = name;
        }
        return workspace ? workspace.workspaceName : '';
    }
});

export const workspaceList = reactive({
    workspaces: workspaceData,
    addWorkspace(workspace) {
        this.workspaces.push(workspace);
    },
    removeWorkspace(id) {
        const index = this.workspaces.findIndex(ws => ws.workspaceId === id);
        if (index > -1) {
            this.workspaces.splice(index, 1);
        }
    }
});

export function initializeWorkspaceInfo() {
    const route = useRouter();
    workspaceInfo.workspaceId = route.current.params.workspaceId;
}