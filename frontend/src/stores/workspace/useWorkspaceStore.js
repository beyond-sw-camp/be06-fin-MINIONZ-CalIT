// useWorkspaceStore.js
import { reactive } from 'vue';
import { workspaceData } from "@/static/workspaceData";

export const workspaceStore = reactive({
    workspaceId: null,
    workspaceName: '',
    setWorkspaceId(id) {
        console.log(`Setting workspaceId to ${id}`); // Debugging statement
        this.workspaceId = id;
        const workspace = this.getWorkspace(id);
        if (workspace) {
            this.workspaceName = workspace.workspaceName;
        } else {
            console.error(`Workspace with id ${id} not found`);
            this.workspaceName = '';
        }
    },
    getWorkspace(id) {
        console.log(`Getting workspace with id ${id}`); // Debugging statement
        return workspaceData.find(ws => ws.workspaceId === id);
    },
    setWorkspaceName(id, name) {
        const workspace = this.getWorkspace(id);
        if (workspace) {
            workspace.workspaceName = name;
        } else {
            console.error(`Workspace with id ${id} not found`);
        }
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