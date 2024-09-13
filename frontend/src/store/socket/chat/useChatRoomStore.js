// 채팅방 목록 조회(GET) /chat/roomList | fetchChatRooms()
// 채팅방 수정(PATCH) /chatroom/update | updateChatRoom()
// 채팅방 나가기(DELETE) /chatroom/{chatroomId}/participants/{userId} | leaveChatRoom()
// 채팅방 검색(GET) /chatroom/search | searchChatRooms()

import { defineStore } from 'pinia';
import axios from 'axios';

export const useChatRoomStore = defineStore('chatRoom', {
    state: () => ({
        chatRooms: [],
        searchResults: [],
    }),
    actions: {
        // 1. 채팅방 목록 조회
        // async fetchChatRooms() {
        //     try {
        //         const response = await axios.get('/chat/roomList');
        //         if (response.data.success) {
        //             this.chatRooms = response.data.result;
        //         }
        //     } catch (error) {
        //         console.error('Error fetching chat rooms:', error);
        //     }
        // },

        // 2. 채팅방 수정
        async updateChatRoom(chatroomId, chatRoomName) {
            try {
                const response = await axios.patch('/chatroom/update', {
                    chatroomId,
                    chatRoomName,
                });
                if (response.data.isSuccess) {
                    const updatedRoom = response.data.result;
                    const index = this.chatRooms.findIndex(room => room.chatroomId === chatroomId);
                    if (index !== -1) {
                        this.chatRooms[index].chatRoomName = updatedRoom.chatRoomName;
                    }
                }
            } catch (error) {
                console.error('Error updating chat room:', error);
            }
        },

        // 3. 채팅방 나가기
        async leaveChatRoom(chatroomId, userId) {
            try {
                const response = await axios.delete(`/chatroom/${chatroomId}/participants/${userId}`);
                if (response.data.isSuccess) {
                    this.chatRooms = this.chatRooms.filter(room => room.chatroomId !== chatroomId);
                }
            } catch (error) {
                console.error('Error leaving chat room:', error);
            }
        },

        // 4. 채팅방 검색
        async searchChatRooms(searchTerm) {
            try {
                const response = await axios.get('/chatroom/search', {
                    params: { query: searchTerm }
                });
                if (response.data.isSuccess) {
                    this.searchResults = response.data.result;
                }
            } catch (error) {
                console.error('Error searching chat rooms:', error);
            }
        },
    },
});