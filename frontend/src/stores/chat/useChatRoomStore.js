import {ref} from "vue";
import {chatRoomList, chatRoomList as initialChatRoomList} from '@/static/chatData';
export const useChatRoomStore = () => {
    const chatRoom = ref(initialChatRoomList);
    const newChatRoomId = ref(0);

    const addChatRoom = ({ name, participants }) => {
        if (!name || !Array.isArray(participants) || participants.length === 0) {
            throw new Error('Invalid data: name and participants are required.');
        }

        const newChatRoomId = ref(0);
        newChatRoomId.value = chatRoomList.length ? Math.max(0, ...chatRoomList.map(room => room.id || 0)) + 1 : 1;
        const newChatRoom = {
            id: newChatRoomId.value,
            name,
            participants
        };
        chatRoom.value.push(newChatRoom);
        return newChatRoomId.value;
    };

    const fetchChatRooms = () => {
        chatRoom.value = initialChatRoomList;
    };

    return {
        chatRoom,
        newChatRoomId,
        addChatRoom,
        fetchChatRooms,
    };
};