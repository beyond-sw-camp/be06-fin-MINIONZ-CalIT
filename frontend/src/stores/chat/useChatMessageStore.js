import axios from 'axios';
import {setPersona} from "@/utils/personaUtils";

export const useChatMessageStore = () => {
    //[MESSAGE] 채팅 보내기 /room/{chatRoomId}/send
    const sendMessage = async ({ chatroomId, messageContents }) => {
        try {
            const response = await axios.post('/room/{chatRoomId}/send', {
                chatroomId,
                messageContents
            });
            return response.data;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [POST] 파일 보내기 /message/sendFile
    const sendFile = async ( file ) => {
        try {
            const formData = new FormData();
            formData.append('file', file);
            const response = await axios.post('/message/sendFile', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            return response.data;
        } catch (error) {
            console.error(error);
            return null;
        }
    }

    // [GET] 채팅 내역 조회 /message/{chatroomId}
    const fetchChatMessages = async (chatroomId) => {
        try {
            const response = await axios.get(`/message/${chatroomId}`);
            const messages = response.data.map(message => {
                const persona = setPersona(message.personaId);
                return {...message, persona};
            });
            return messages;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [MESSAGE] 채팅 수정 /room/{chatRoomId}/edit
    const editMessage = async ({ chatroomId, messageId, messageContents }) => {
        try {
            const response = await axios.patch(`/room/{chatRoomId}/edit`, {
                chatroomId,
                messageId,
                messageContents
            });
            return response.data;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [DELETE]  채팅  삭제 /message/{messageId}
    const deleteMessage = async (messageId) => {
        try {
            const response = await axios.delete(`/message/${messageId}`);
            return response.data;
        } catch (error) {
            console.error(error);
            return null;
        }
    };


    return {
        sendMessage,
        sendFile,
        fetchChatMessages,
        editMessage,
        deleteMessage
    };
}