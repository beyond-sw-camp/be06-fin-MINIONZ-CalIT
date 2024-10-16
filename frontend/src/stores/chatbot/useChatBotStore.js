import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';

export const useChatBotStore = defineStore('chatBot', {
  state: () => ({
    botMessageList: [], // 챗봇과의 메시지 기록을 저장할 리스트
  }),

  actions: {
    // [POST] n8n에서 받은 응답 처리 : /receiveFromN8n
    async receiveFromN8n({ botQuestionId, message }) {
      try {
        const payload = {
          botQuestionId: botQuestionId,
          message: message,
        };
        const response = await axiosInstance.post(
          '/api/receiveFromN8n',
          payload
        );
        return response.data.result;
      } catch (error) {
        console.error(
          'n8n 응답 에러:',
          error.response ? error.response.data : error.message
        );
        return null;
      }
    },
  },
});
