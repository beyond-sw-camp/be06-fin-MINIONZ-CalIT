import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";

export const useChatMessageStore = defineStore('chatMessage', {
    state: () => ({
        chatMessageList: [],
        otherMessageList: [],
        chatRoomList: [],
        sm: null
    }),

    actions: {
        // [POST] 파일 보내기 /message/sendFile
        async sendFile({ files, chatRoomId }) {
            try {
                const formData = new FormData();
                formData.append('chatRoomId', chatRoomId);
                formData.append('files', files); // 단일 파일 처리

                const response = await axiosInstance.post('/api/message/sendFile', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                return response.data.result; // 파일 URL 반환
            } catch (error) {
                console.error('파일 업로드 에러:', error.response ? error.response.data : error.message);
                return null;
            }
        },

        // [GET] 채팅 내역 조회 /message/{chatroomId}
        async fetchChatMessages(chatroomId) {
            try {
                const response = await axiosInstance.get(`/api/message/${chatroomId}`);
                // 메시지를 역순으로 정렬하여 반환
                const sortedMessages = response.data.result;
                console.log(response.data.result);
                return sortedMessages;
            } catch (error) {
                console.error(error);
                return null;
            }
        },

        // [PATCH] 메세지 상태 변경
        async changeMessageStatus(chatRoomId) {
            try {
                const response = await axiosInstance.patch(`/api/message/${chatRoomId}`);
                return response.data.result;
            } catch (error) {
                console.error(error);
                return null;
            }
        },

        // [PATCH] 채팅 수정 /room/{chatRoomId}/edit
        async editMessage({ chatRoomId, messageContents, userName, messageId }) {
            try {
                const response = await axiosInstance.patch(`/api/room/${chatRoomId}/edit`, {
                    chatRoomId, messageContents, userName, messageId
                });
                return response.data.result;
            } catch (error) {
                console.error(error);
                return null;
            }
        },

        // [DELETE] 채팅 삭제 /message/{messageId}
        async deleteMessage(messageId) {
            try {
                const response = await axiosInstance.delete(`/api/message/${messageId}`);
                return response.data.result;
            } catch (error) {
                console.error(error);
                return null;
            }
        },
    }
});
