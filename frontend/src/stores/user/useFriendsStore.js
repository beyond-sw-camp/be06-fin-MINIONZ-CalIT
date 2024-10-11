import {ref} from 'vue';
import {axiosInstance} from '@/utils/axiosInstance';
import {defineStore} from 'pinia';

export const useFriendsStore = defineStore('friendsStore', () => {
    const friends = ref([]);
    const participantsName = ref([]);
    const filteredUsers = ref([]);
    const selectedParticipants = ref([]);
    const getUserList = async (loginId) => {
        try {
            const response = await axiosInstance.get(
                `/api/search/containeduser?loginId=${loginId}`
            );
            console.log('API response:', response);
            return response.data.result;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    };

    const getFriendsList = async (workspaceId) => {
        try {
            const response = await axiosInstance.get(
                `/api/search/workspaceuser?workspaceId=${workspaceId}`
            );
            friends.value = response.data.result;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    };

    const filterUsers = () => {
        filteredUsers.value = friends.value.filter(friend => friend.userName.includes(participantsName.value));
    }

    const participants = () => {

    }

    const addParticipant = (participant) => {
        selectedParticipants.value.push(participant);
    }

    const removeParticipant = () => {

    }

    return {
        friends,
        getUserList,
        getFriendsList,
        participantsName,
        participants,
        filteredUsers,
        removeParticipant,
        filterUsers,
        addParticipant,
        selectedParticipants
    };
});
