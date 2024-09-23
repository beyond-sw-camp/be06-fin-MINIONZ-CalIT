import { useChatRoomStore } from '@/stores/socket/chat/useChatRoomStore';
import { useChatMessageStore } from "@/stores/socket/chat/useChatMessageStore";
// import { useAlarmStore } from '@/stores/socket/useAlarmStore';
import { useDocsEditStore } from '@/stores/socket/useDocsEditStore';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient;

export function connectWebSocket(chatRoomId) {
    const socket = new SockJS('http://localhost:8088/chat'); // SockJS를 사용해 WebSocket 연결
    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000, // 자동 재연결 설정
        onConnect: () => {
            console.log('STOMP WebSocket connected');

            // 채팅 메시지 구독
            stompClient.subscribe(`/sub/room/${chatRoomId}`, (message) => {
                const data = JSON.parse(message.body);
                const chatRoomStore = useChatRoomStore();
                const chatMessageStore = useChatMessageStore();
                chatMessageStore.sendMessage(chatRoomStore.$id, data);
            });

            // 알림 구독
            // stompClient.subscribe('/topic/notifications', (message) => {
            //     const data = JSON.parse(message.body);
            //     // const notificationStore = useNotificationStore();
            //     // notificationStore.addNotification(data.notification);
            // });

            // 문서 수정 구독
            stompClient.subscribe('/topic/documentUpdate', (message) => {
                const data = JSON.parse(message.body);
                const documentStore = useDocsEditStore();
                documentStore.updateContent(data.content);
            });
        },
        onStompError: (frame) => {
            console.error('STOMP error', frame);
        }
    });

    stompClient.activate();  // WebSocket 활성화
}

export function sendMessageToServer(chatRoomId, messageContent, files=[]) {
    const message = {
        messageContents: messageContent,
        files: files,
        chatRoomId: chatRoomId,
    };
    stompClient.send(`/pub/room/${chatRoomId}/send`, {}, JSON.stringify(message));
}