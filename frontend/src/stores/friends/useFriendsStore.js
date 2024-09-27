import { ref } from 'vue';
import { userData } from "@/static/userData";

export function useFriendsStore() {
    const friends = ref(userData || []);

    const getFilteredUsers = (workspaceId, searchTerm) => {
        let users = Array.isArray(friends.value[workspaceId]) ? friends.value[workspaceId] : [];
        return users.filter(user => user.name.toLowerCase().includes(String(searchTerm).toLowerCase()));
    };

    return {
        getFilteredUsers
    };
}