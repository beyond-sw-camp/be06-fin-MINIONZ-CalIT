import { ref } from "vue";
import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";

export const useChatRoomStore = defineStore('chatRoomStore', () => {
    const chatRoom = ref([]);
    const newChatRoomId = ref(0);


    // [POST] 그룹 채팅 방 생성 /chat/room
    const addChatRoom = async ({chatroomName, participants}) => {
        if (!Array.isArray(participants) || participants.length === 0) {
            throw new Error('Invalid data: participants are required.');
        }

        if (!chatroomName) {
            chatroomName = participants.join(', ');
        }

        const newChatRoomId = ref(0);
        try {
            const response = await axiosInstance.post('/api/chat/room', {
                chatroomName,
                participants
            });

            if (response.data.success) {
                newChatRoomId.value = response.data.chatroomId;
                chatRoom.value.push({
                    chatroomId: newChatRoomId.value,
                    chatRoomName: chatroomName,
                    participants: participants
                });
            } else {
                throw new Error('Failed to create chat room');
            }
        } catch (error) {
            console.error('Error creating chat room:', error);
        }
    };


    // [GET] 채팅 방 목록 조회
    const fetchChatRooms = async (workspaceId) => {
        try {
            const response = await axiosInstance.get(`/api/chat/${workspaceId}/roomList`);
            chatRoom.value = response.data.result;
        } catch (error) {
            console.error('Error fetching chat rooms:', error);
        }
    }

    // [PATCH] 채팅 방 수정
    const updateChatRoom = async ({chatroomId, newRoomName}) => {
        try {
            const response = await axiosInstance.patch(`/api/chat/room/${chatroomId}/edit`, {
                newRoomName
            });

            if (response.data.success) {
                const index = chatRoom.value.findIndex(room => room.chatroomId === chatroomId);
                chatRoom.value[index] = {
                    chatroomId,
                    newRoomName
                };
            } else {
                throw new Error('Failed to update chat room');
            }
        } catch (error) {
            console.error('Error updating chat room:', error);
        }
    };

    // [DELETE] 채팅 방 나가기  /chat/{chatroomId}/exit
    const exitChatRoom = async (chatroomId) => {
        try {
            const response = await axiosInstance.delete(`/api/chat/${chatroomId}/exit`);

            if (response.data.success) {
                chatRoom.value = chatRoom.value.filter(room => room.chatroomId !== chatroomId);
            } else {
                throw new Error('Failed to exit chat room');
            }
        } catch (error) {
            console.error('Error exiting chat room:', error);
        }
    };

    return {
        chatRoom,
        newChatRoomId,
        addChatRoom,
        fetchChatRooms,
        updateChatRoom,
        exitChatRoom
    }
})