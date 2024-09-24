import {ref} from "vue";
import {chatRoomList, chatRoomList as initialChatRoomList} from '@/static/chatData';
import axios from "axios";
export const useChatRoomStore = () => {
    const chatRoom = chatRoomList;
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
            const response = await axios.post('/chat/room', {
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
    const fetchChatRooms = () => {
        chatRoom.value = initialChatRoomList;
    };

    // [PATCH] 채팅 방 수정

    return {
        chatRoom,
        newChatRoomId,
        addChatRoom,
        fetchChatRooms,
    };
};