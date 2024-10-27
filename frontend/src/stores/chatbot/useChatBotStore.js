import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';

export const useChatBotStore = defineStore('chatBot', {
  state: () => ({
    botMessageList: [],
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

    // [GET] 사용자 메시지 내역 불러오기
    async loadBotMessages(userId) {
      try {
        const response = await axiosInstance.get(`/api/botMessage/${userId}`);
        this.botMessageList = response.data;
        return response.data; // 데이터를 반환
      } catch (error) {
        console.error(
          '메시지 불러오기 에러:',
          error.response ? error.response.data : error.message
        );
        return null; // 오류 발생 시 null 반환
      }
    },
  },
});
