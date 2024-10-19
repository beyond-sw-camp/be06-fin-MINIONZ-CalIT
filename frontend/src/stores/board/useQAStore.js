import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";
import {ref} from "vue";

export const useQAStore = defineStore('qaStore', () => {
    const postList = ref([]);
    const postDetail = ref({});

    // [POST] 글 작성 /qaboard/write
    const writePost = async ({workspaceId, taskId, qaboardTitle, qaboardContent, workspaceParticipationId, formData}) => {
        try {
            formData.append('request', JSON.stringify({
                qaboardTitle,
                qaboardContent,
                taskId,
                workspaceParticipationId
            }));
            const response = await axiosInstance.post(`/api/qaboard/write?workspaceId=${workspaceId}`, formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            });
            return response.data.result;
        } catch (error) {
            console.error('Error writing post:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [GET] 글 상세 조회 /qaboard/search
    const getPostDetail = async (boardId) => {
        try {
            const response = await axiosInstance.get(`/api/qaboard/search?boardId=${boardId}`);
            postDetail.value = response.data.result || {};
            return response.data.result;
        } catch (error) {
            console.error('Error fetching post detail:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [GET] 글 목록 조회 /qaboard/search-all
    const getPostList = async (workspaceId, page, size) => {
        try {
            page = page - 1;
            const response = await axiosInstance.get(`/api/qaboard/search-all?workspaceId=${workspaceId}&page=${page}&size=${size}`);
            postList.value = response.data.result.content;
            return response.data.result.content;
        } catch (error) {
            console.error('Error fetching post list:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [PUT] 글 업데이트 /qaboard/update
    const updatePost = async (data) => {
        try {
            const response = await axiosInstance.put('/api/qaboard/update', data);
            return response.data.result;
        } catch (error) {
            console.error('Error updating post:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [DELETE] 글 삭제 /qaboard/delete
    const deletePost = async (id) => {
        try {
            const response = await axiosInstance.delete('/api/qaboard/delete', { data: { id } });
            return response.data.result;
        } catch (error) {
            console.error('Error deleting post:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [POST] 댓글 작성 /qacomment/write
    const writeComment = async ({ files, qaboardTitle, qaboardContent, progressStatus }) => {
        try {
            const formData = new FormData();
            formData.append('request', JSON.stringify({
                qaboardTitle,
                qaboardContent,
                progressStatus
            }));

            // 파일이 있는 경우에만 추가
            if (files && files.length > 0) {
                files.forEach((file) => {
                    formData.append('files', file);
                });
            }

            const response = await axiosInstance.post(`/api/qacomment/write`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            return response.data.result;
        } catch (error) {
            console.error('Error writing comment:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [GET] 댓글 조회 /qacomment/search
    const getCommentList = async (qaboardId) => {
        try {
            const response = await axiosInstance.get('/api/qacomment/search', { params: { qaboardId } });
            return response.data.result;
        } catch (error) {
            console.error('Error fetching comments:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    const searchQaBoardByKeyword = async({workspaceId, page, size, keyword}) => {
        try {
            const response = await axiosInstance.get('/api/qa/search-keyword', {
                params: { workspaceId, page, size, keyword }
            });
            return response.data.result.content;
        } catch (error) {
            console.error('키워드로 게시글 검색 중 오류가 발생했습니다:', error);
            throw error;
        }
    }

    return {
        postList,
        writePost,
        getPostList,
        getPostDetail,
        updatePost,
        deletePost,
        writeComment,
        getCommentList,
        searchQaBoardByKeyword
    };
});
