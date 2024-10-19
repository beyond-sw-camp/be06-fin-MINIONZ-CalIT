import router from '@/router';

export const navigateWithWorkspaceId = (path, wsId) => {
    router.push({
        path: path,
        query: { wsId }
    });
};