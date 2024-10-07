import { ref } from 'vue';
import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from 'pinia';

export const useFriendsStore = defineStore('friendsStore', () => {
    const friends = ref([]);

    const getUserList = async (loginId) => {
        try {
            const response = await axiosInstance.get(`/api/search/containeduser?loginId=${loginId}` );
            console.log('API response:', response);
            friends.value = response.data;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    };

    const getFriendsList = async ({workspaceId, userId, name }) => {
        try {
            const response = await axiosInstance.get(`/api/friends/search?workspaceId=${workspaceId}&id=${userId}&name=${name}`);
            friends.value = response.data.result;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    }

    return {
        friends,
        getUserList,
        getFriendsList
    };
});