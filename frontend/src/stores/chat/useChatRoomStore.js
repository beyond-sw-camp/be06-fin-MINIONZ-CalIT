import {ref} from "vue";
import {chatRoomList, chatRoomList as initialChatRoomList} from '@/static/chatData';
export const useChatRoomStore = () => {
    const chatRoom = ref(initialChatRoomList);
    const newChatRoomId = ref(0);

    const addChatRoom = ({ chatroomName, participants }) => {
        if (!Array.isArray(participants) || participants.length === 0) {
            throw new Error('Invalid data: participants are required.');
        }

        if (!chatroomName) {
            chatroomName = participants.join(', ');
        }

        const newChatRoomId = ref(0);
        // newChatRoomId.value = chatRoomList.length ? Math.max(0, ...chatRoomList.map(room => room.id || 0)) + 1 : 1;
        newChatRoomId.value = chatRoomList.length + 1;
        chatRoom.push({
            chatroomId: newChatRoomId.value,
            chatRoomName: chatroomName,
            participants: participants,
            messageContents: '',
            createdAt: new Date().toISOString(),
            unreadMessages: 0,
            profilePic: ''
        })
        // const newChatRoom = {
        //     id: newChatRoomId,
        //     chatroomName,
        //     participants
        // };
        // chatRoom.value.push(newChatRoom);
        return newChatRoomId;
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