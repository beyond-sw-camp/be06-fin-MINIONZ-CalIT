import {axiosInstance} from "@/utils/axiosInstance";
import {defineStore} from "pinia";
// import {setPersona} from "@/utils/personaUtils";

export const useChatMessageStore = defineStore('chatMessage', () => {
    //[MESSAGE] 채팅 보내기 /room/{chatRoomId}/send
    const sendMessage = async ({ messageContents, chatRoomId, userId, userName }) => {
        try {
            const response = await axiosInstance.post(`/api/room/${chatRoomId}/send`, {
                chatRoomId,
                messageContents,
                userId,
                userName
            });
            return response.data.result;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [POST] 파일 보내기 /message/sendFile
    const sendFile = async ( {files, chatRoomId} ) => {
        try {
            const formData = new FormData();
            formData.append('file', files);
            const response = await axiosInstance.post('/api/message/sendFile', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
                params: {
                    chatRoomId
                }
            });
            return response.data.result;
        } catch (error) {
            console.error(error);
            return null;
        }
    }

    // [GET] 채팅 내역 조회 /message/{chatroomId}
    const fetchChatMessages = async (chatroomId) => {
        try {
            const response = await axiosInstance.get(`/api/message/${chatroomId}`);
            return response.data.result;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [GET] 메세지 상태 변경
    const changeMessageStatus = async (chatRoomId) => {
        try {
            const response = await axiosInstance.patch(`/api/message/${chatRoomId}`);
            return response.data.result;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [MESSAGE] 채팅 수정 /room/{chatRoomId}/edit
    const editMessage = async ({ chatRoomId,  messageContents, userName, messageId, }) => {
        try {
            const response = await axiosInstance.patch(`/api/room/${chatRoomId}/edit`, {
                chatRoomId, messageContents, userName, messageId
            });
            return response.data.result;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    // [DELETE]  채팅  삭제 /message/{messageId}
    const deleteMessage = async (messageId) => {
        try {
            const response = await axiosInstance.delete(`/api/message/${messageId}`);
            return response.data.result;
        } catch (error) {
            console.error(error);
            return null;
        }
    };


    return {
        sendMessage,
        sendFile,
        fetchChatMessages,
        changeMessageStatus,
        editMessage,
        deleteMessage
    };
})