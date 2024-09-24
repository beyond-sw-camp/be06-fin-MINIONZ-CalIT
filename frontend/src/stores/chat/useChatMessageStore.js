import axios from 'axios';

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

    return {
        sendMessage,
        sendFile
    };
}