import { axiosInstance } from '@/utils/axiosInstance';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useErrorStore = defineStore('errorBoard', () => {
  const errorBoards = ref([]);
  const errorDetail = ref({});

  // [GET] 게시글 목록 조회
  const getErrorBoardList = async (workspaceId, page, size) => {
    try {
      page = page - 1; // 페이지 번호를 0부터 시작하도록 조정
      const response = await axiosInstance.get('/api/errboard/search-all', {
        params: { workspaceId, page, size },
      });
      errorBoards.value = response.data.result.content;
      return errorBoards.value;
    } catch (error) {
      console.error('게시글 목록 조회 중 오류가 발생했습니다:', error);
      return error.response?.data || { message: 'Unknown error occurred' };
    }
  };

  // [GET] 게시글 상세 조회
  const getErrorBoard = async (boardId) => {
    try {
      const response = await axiosInstance.get('/api/errboard/search', {
        params: { boardId },
      });
      errorDetail.value = response.data.result;
      return response.data.result;
    } catch (error) {
      console.error('게시글 조회 중 오류가 발생했습니다:', error);
      return error.response?.data || { message: 'Unknown error occurred' };
    }
  };

  // [GET] 키워드로 게시글 검색
  const searchErrorBoardByKeyword = async (
    workspaceId,
    page,
    size,
    keyword
  ) => {
    try {
      const response = await axiosInstance.get('/api/errboard/search-keyword', {
        params: { workspaceId, page, size, keyword },
      });
      errorBoards.value = response.data.result.content;
      return errorBoards.value;
    } catch (error) {
      console.error('키워드로 게시글 검색 중 오류가 발생했습니다:', error);
      return error.response?.data || { message: 'Unknown error occurred' };
    }
  };

  // [GET] 카테고리별로 게시글 검색
  const searchErrorBoardByCategory = async (
    workspaceId,
    page,
    size,
    category
  ) => {
    try {
      const response = await axiosInstance.get(
        '/api/errboard/search-category',
        {
          params: { workspaceId, page, size, category },
        }
      );
      errorBoards.value = response.data.result.content;
      return errorBoards.value;
    } catch (error) {
      console.error('카테고리별 게시글 검색 중 오류가 발생했습니다:', error);
      return error.response?.data || { message: 'Unknown error occurred' };
    }
  };

  // [PUT] 게시글 업데이트
  const updateErrorBoard = async (data) => {
    try {
      const response = await axiosInstance.put('/api/errboard/update', data);
      return response.data.result;
    } catch (error) {
      console.error('게시글 업데이트 중 오류가 발생했습니다:', error);
      return error.response?.data || { message: 'Unknown error occurred' };
    }
  };

  // [DELETE] 게시글 삭제
  const deleteErrorBoard = async (id) => {
    try {
      const response = await axiosInstance.delete('/api/errboard/delete', {
        data: { id },
      });
      return response.data.result;
    } catch (error) {
      console.error('게시글 삭제 중 오류가 발생했습니다:', error);
      return error.response?.data || { message: 'Unknown error occurred' };
    }
  };

  return {
    errorBoards,
    errorDetail,
    getErrorBoardList,
    getErrorBoard,
    searchErrorBoardByKeyword,
    searchErrorBoardByCategory,
    updateErrorBoard,
    deleteErrorBoard,
  };
});
