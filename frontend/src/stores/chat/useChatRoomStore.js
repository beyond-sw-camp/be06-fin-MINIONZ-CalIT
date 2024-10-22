import { ref } from 'vue';
import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { useRoute } from 'vue-router';

export const useChatRoomStore = defineStore('chatRoomStore', () => {
  const route = useRoute();
  const chatRoom = ref([]);
  const newChatRoomId = ref(0);
  const chatroomId = ref(null);

  // [POST] 그룹 채팅 방 생성 /chat/room
  const addChatRoom = async (data) => {
    if (!Array.isArray(data.participants) || data.participants.length === 0) {
      throw new Error('Invalid data: participants are required.');
    }

    if (!data.chatRoomName || data.chatRoomName.trim() === '') {
      data.chatRoomName = data.participants.join(', ');
    } else {
      console.log('ok');
    }

    try {
      const response = await axiosInstance.post('/api/chat/room', data);
      if (response.data.success) {
        chatRoom.value.push({
          chatRoomId: response.data.result.chatRoomId,
          chatRoomName: response.data.result.chatRoomName,
          participants: response.data.result.participants,
        });
        newChatRoomId.value = response.data.result.chatRoomId;
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
      const response = await axiosInstance.get(
        `/api/chat/${workspaceId}/roomList`
      );
      chatRoom.value = response.data.result || [];
    } catch (error) {
      console.error('Error fetching chat rooms:', error);
    }
  };

  // [PATCH] 채팅 방 수정
  const updateChatRoom = async ({ chatroomId, newRoomName }) => {
    try {
      const response = await axiosInstance.patch(
        `/api/chat/room/${chatroomId}/edit`,
        {
          newRoomName,
        }
      );

      if (response.data.success) {
        const index = chatRoom.value.findIndex(
          (room) => room.chatroomId === chatroomId
        );
        chatRoom.value[index] = {
          chatroomId,
          newRoomName,
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
      const response = await axiosInstance.delete(
        `/api/chat/${chatroomId}/exit`
      );

      if (response.data.success) {
        chatRoom.value = chatRoom.value.filter(
          (room) => room.chatroomId !== chatroomId
        );
      } else {
        throw new Error('Failed to exit chat room');
      }
    } catch (error) {
      console.error('Error exiting chat room:', error);
    }
  };

  const setChatroomId = (id) => {
    newChatRoomId.value = id;
    chatroomId.value = id;
    route.params.chatroomId = id;
  };

  return {
    chatRoom,
    newChatRoomId,
    addChatRoom,
    fetchChatRooms,
    updateChatRoom,
    exitChatRoom,
    setChatroomId,
  };
});
