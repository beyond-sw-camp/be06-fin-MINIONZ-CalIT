import { defineStore } from 'pinia';
import { axiosInstance } from '@/utils/axiosInstance';  // axiosInstance를 가져옵니다

export const useErrorStore = defineStore('errorBoard', {
  state: () => ({
    errorBoards: [],  // 게시판 리스트를 저장하는 상태
  }),
  actions: {
    // 게시글 생성
    async getErrorBoardList(workspaceId, page, size) {
      try {
        const response = await axiosInstance.get('/api/errboard/search-all', {
          params: { workspaceId: workspaceId, page: page - 1, size: size }
        });
        this.errorBoards = response.data.result.content;
        return this.errorBoards;
      } catch (error) {
        console.error('게시글 목록 조회 중 오류가 발생했습니다:', error);
        throw error;
      }
    },
    
    // 게시글 하나 조회
    async getErrorBoard(boardId) {
      try {
        const response = await axiosInstance.get('/api/errboard/search', {
          params: { boardId }
        });
        return response.data.result;
      } catch (error) {
        console.error('게시글 조회 중 오류가 발생했습니다:', error);
        throw error;
      }
    },
    
    // 키워드로 게시글 검색
    async searchErrorBoardByKeyword(workspaceId, page, size, keyword) {
      try {
        const response = await axiosInstance.get('/api/errboard/search-keyword', {
          params: { workspaceId, page, size, keyword }
        });
        return response.data.result.content;
      } catch (error) {
        console.error('키워드로 게시글 검색 중 오류가 발생했습니다:', error);
        throw error;
      }
    },

    // 카테고리별로 게시글 검색
    async searchErrorBoardByCategory(workspaceId, page, size, category) {
      try {
        const response = await axiosInstance.get('/api/errboard/search-category', {
          params: { workspaceId, page, size, category }
        });
        return response.data.result.content;
      } catch (error) {
        console.error('카테고리별 게시글 검색 중 오류가 발생했습니다:', error);
        throw error;
      }
    }
  }
});
