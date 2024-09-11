// 채팅 보내기(POST) | /message/send | sendMessage
// 파일 보내기((POST) /message/sendFile | sendFile
// 채팅 내역 조회(GET) /chatroom/{chatroomId}/message | fetchMessages
// 채팅 수정(PUT) /chatRoom/{chatroomId}/message/{messageId} | updateMessage
// 채팅 삭제(DELETE) /chatRoom/{chatroomId}/message/{messageId} | deleteMessage

import { defineStore } from 'pinia';
import axios from 'axios';

export const useChatMessageStore = defineStore('chatMessage', {
    state: () => ({
        messages: [],
    }),
    actions: {
        // 1. 채팅 메시지 보내기 (텍스트)
        async sendMessage(chatRoomId, messageContents) {
            try {
                const response = await axios.post('/message/send', {
                    chatRoomId,
                    messageContents,
                });
                if (response.data.success) {
                    this.messages.push(response.data.result);
                }
            } catch (error) {
                console.error('Error sending message:', error);
            }
        },

        // 2. 파일 전송
        async sendFile(chatRoomId, file) {
            try {
                const formData = new FormData();
                formData.append('chatRoomId', chatRoomId);
                formData.append('files', file);

                const response = await axios.post('/message/sendFile', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });
                if (response.data.success) {
                    this.messages.push(response.data.result);
                }
            } catch (error) {
                console.error('Error sending file:', error);
            }
        },

        // 3. 채팅 내역 조회
        async fetchMessages(chatRoomId) {
            try {
                const response = await axios.get(`/chatroom/${chatRoomId}/message`);
                if (response.data.isSuccess) {
                    this.messages = response.data.result;
                }
            } catch (error) {
                console.error('Error fetching messages:', error);
            }
        },

        // 4. 채팅 메시지 수정
        async updateMessage(chatRoomId, messageId, messageContents) {
            try {
                const response = await axios.put(`/chatRoom/${chatRoomId}/message/${messageId}`, {
                    messageContents,
                });
                if (response.data.isSuccess) {
                    const updatedMessage = response.data.result;
                    const index = this.messages.findIndex(message => message.messageId === messageId);
                    if (index !== -1) {
                        this.messages[index].messageContents = updatedMessage.messageContents;
                        this.messages[index].updatedAt = updatedMessage.updatedAt;
                    }
                }
            } catch (error) {
                console.error('Error updating message:', error);
            }
        },

        // 5. 채팅 메시지 삭제
        async deleteMessage(chatRoomId, messageId) {
            try {
                const response = await axios.delete(`/chatRoom/${chatRoomId}/message/${messageId}`);
                if (response.data.isSuccess) {
                    const index = this.messages.findIndex(message => message.messageId === messageId);
                    if (index !== -1) {
                        this.messages.splice(index, 1);
                    }
                }
            } catch (error) {
                console.error('Error deleting message:', error);
            }
        },
    },
});