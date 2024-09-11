import { useChatRoomStore } from '@/store/socket/chat/useChatRoomStore';
import { useNotificationStore } from '@/store/socket/useNotifyStore';
import { useDocsEditStore } from '@/store/socket/useDocsEditStore';

let socket;

export function connectWebSocket() {
    socket = new WebSocket('ws://localhost:8088');

    socket.onopen = () => {
        console.log('WebSocket connected');
    };

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data);

        // 데이터 타입에 따라 각각의 스토어에 반영
        if (data.type === 'chat') {
            const chatStore = useChatRoomStore();
            chatStore.addMessage(data.message);
        } else if (data.type === 'notification') {
            const notificationStore = useNotificationStore();
            notificationStore.addNotification(data.notification);
        } else if (data.type === 'documentUpdate') {
            const documentStore = useDocsEditStore();
            documentStore.updateContent(data.content);
        }
    };

    socket.onclose = () => {
        console.log('WebSocket disconnected');
    };
}

export function sendMessageToServer(message) {
    socket.send(JSON.stringify(message));
}