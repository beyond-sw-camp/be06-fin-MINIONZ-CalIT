// 그룹 채팅방 생성(POST) /chat/room |
// 개인 채팅방 생성(POST) /chat/room

import { ref } from 'vue';
import axios from 'axios';

export function useChatRoomCreateStore() {
    const chatRoomList = ref([]);
    const loading = ref(false);
    const error = ref(null);

    const fetchChatRooms = async () => {
        loading.value = true;
        error.value = null;
        try {
            const response = await axios.get('/chat/roomList');
            if (response.data.success) {
                chatRoomList.value = response.data.result;
            } else {
                error.value = response.data.message;
            }
        } catch (err) {
            error.value = err.message;
        } finally {
            loading.value = false;
        }
    };

    const createChatRoom = async (chatRoomName, participants) => {
        loading.value = true;
        error.value = null;
        try {
            const response = await axios.post('/chat/room', {
                chatRoomName,
                participants
            });
            if (response.data.success) {
                chatRoomList.value.push(response.data.result);
            } else {
                error.value = response.data.message;
            }
        } catch (err) {
            error.value = err.message;
        } finally {
            loading.value = false;
        }
    };

    return {
        chatRoomList,
        loading,
        error,
        fetchChatRooms,
        createChatRoom,
    };
}