import { defineStore } from 'pinia';
import axios from 'axios';  // S3 업로드를 위한 HTTP 요청 처리
import { sendMessageToServer } from '@/services/websocketService';

export const useChatMessageStore = defineStore('chatMessage', {
    state: () => ({
        messages: [],  // 채팅 메시지 목록
    }),
    actions: {
        // 1. 새로운 메시지를 수신했을 때 메시지 목록에 추가
        addMessage(newMessage) {
            this.messages.push(newMessage);
        },

        // 2. 서버로 메시지 보내기 (텍스트 메시지)
        sendMessage(chatRoomId, messageContents) {
            const message = {
                chatRoomId,
                messageContents,
                sender: 'User1',  // 실제 사용자 정보를 넣어야 함
            };
            sendMessageToServer('/app/sendMessage', message);  // STOMP 메시지 전송
        },

        // 3. 서버로 파일 업로드 후 S3 URL을 WebSocket으로 전송
        async sendFile(chatRoomId, file) {
            try {
                // 1. S3에 파일 업로드
                const formData = new FormData();
                formData.append('file', file);

                const response = await axios.post(`/api/upload`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                });

                // 2. 업로드된 파일의 S3 URL을 WebSocket으로 전송
                if (response.data.success) {
                    const fileUrl = response.data.fileUrl;  // S3 URL
                    const message = {
                        chatRoomId,
                        fileUrl,  // S3 파일 URL
                        fileName: file.name,
                        fileType: file.type,
                        messageType: 'FILE',
                    };
                    sendMessageToServer('/app/sendFile', message);  // STOMP 파일 메시지 전송
                } else {
                    console.error('File upload failed');
                }
            } catch (error) {
                console.error('Error uploading file to S3:', error);
            }
        },

        // 4. 채팅 메시지 목록 조회
        fetchMessages(chatRoomId) {
            // 메시지를 WebSocket 구독을 통해 실시간으로 가져오므로, 서버로 구독 요청만 합니다.
            console.log(`Fetching messages for chatRoomId: ${chatRoomId}`);
        },
    },
});