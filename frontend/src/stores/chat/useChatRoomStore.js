import {ref} from "vue";
import { chatRoomList } from '@/static/chatData';
export const useChatRoomStore = () => {
    const chatRoom = ref(chatRoomList);
    const newChatRoomId = ref(null);

    const addChatRoom = ({ name, participants }) => {
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

    return {
        chatRoom,
        newChatRoomId,
        addChatRoom,
    };
};