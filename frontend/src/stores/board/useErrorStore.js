import { defineStore } from 'pinia';
import { axiosInstance } from '@/utils/axiosInstance';  // axiosInstance를 가져옵니다

export const useErrorStore = defineStore('errorBoard', {
  state: () => ({
    errorBoards: [],  // 게시판 리스트를 저장하는 상태
  }),
  actions: {
    // 게시글 생성
    async createErrorBoard(taskId, errboardTitle, errboardContent, category, files, workspaceId) {
      try {
        const formData = new FormData();
        formData.append('request', JSON.stringify({
          taskId,
          errboardTitle,
          errboardContent,
          category
        }));

        // 파일이 있는 경우에만 파일 추가
        if (files.length > 0) {
          files.forEach((file) => {
            formData.append('files', file);
          });
        }

        console.log('전송할 FormData:', formData);  // FormData가 잘 생성되었는지 확인

        // API 요청 전송
        const response = await axiosInstance.post(`/api/errboard/write?workspaceId=${workspaceId}`, formData, {
          headers: {
        
          }
        });

        console.log('API 응답:', response);  // 응답 전체 확인

        if (response && response.data && response.data.result) {
          return response.data.result;
        } else {
          throw new Error('result 속성이 응답에 포함되지 않았습니다.');
        }
      } catch (error) {
        if (error.response) {
          console.error('서버 응답 오류:', error.response);  // 서버 오류 응답 확인
        } else if (error.request) {
          console.error('요청 자체가 전송되지 않음:', error.request);  // 요청 자체가 실패한 경우
        } else {
          console.error('기타 오류:', error.message);  // 기타 오류 메시지 출력
        }
        throw error;
      }
    },
    async getErrorBoardList(workspaceId, page, size) {
      try {
        const response = await axiosInstance.get(`/api/errboard/search-all`, {
          params: {
            workspaceId: workspaceId,  // 올바르게 workspaceId를 전달
            page: page - 1,            // 페이지는 0부터 시작하는 경우
            size: size
          }
        });
        this.errorBoards = response.data.result.content;
        return this.errorBoards;
      } catch (error) {
        console.error('게시글 목록 조회 중 오류가 발생했습니다:', error);
        throw error;
      }
    }
    
    
    
,
    
    // 게시글 하나 조회
    async getErrorBoard(boardId) {
      try {
        const response = await axiosInstance.get(`/errboard/search`, {
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
        const response = await axiosInstance.get(`/errboard/search-keyword`, {
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
        const response = await axiosInstance.get(`/errboard/search-category`, {
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
