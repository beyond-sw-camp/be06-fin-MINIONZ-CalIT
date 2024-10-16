import { ref, watch } from "vue";
import { useRoute } from "vue-router";

export function setWorkspaceId() {
    const route = useRoute();
    const workspaceId = ref(route.params.workspaceId);

    watch(
        () => route.params.workspaceId,
        (newId) => {
            workspaceId.value = newId;
        }
    );

    return workspaceId;
}

export function setWorkspaceName() {
    const route = useRoute();
    const workspaceName = ref(route.params.workspaceName);

    watch(
        () => route.params.workspaceName,
        (newName) => {
            workspaceName.value = newName;
        }
    );

    return workspaceName;
}