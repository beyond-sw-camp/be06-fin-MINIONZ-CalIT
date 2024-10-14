import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";

export const useQAStore = defineStore('qaStore', () => {
    // [POST] 글 작성 /qaboard/write
    const writePost = async ({ files, qaboardTitle, qaboardContent, taskId, workspaceId,workspaceParticipationId  }) => {
        try {
            const formData = new FormData();
            formData.append('request', JSON.stringify({
                taskId,
                qaboardTitle,
                qaboardContent,
                workspaceId,
                workspaceParticipationId 
            }));

            // 파일이 있는 경우에만 추가
            if (files && files.length > 0) {
                files.forEach((file) => {
                    formData.append('files', file);
                });
            }

            const response = await axiosInstance.post('/api/qaboard/write', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
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
            const response = await axiosInstance.get(`/qaboard/search`, { params: { boardId } });
            return response.data.result;
        } catch (error) {
            console.error('Error fetching post detail:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [GET] 글 목록 조회 /qaboard/search-all
    const getPostList = async (workspaceId, page, size) => {
        try {
            const response = await axiosInstance.get('/qaboard/search-all', {
                params: { workspaceId, page: page - 1, size }
            });
            return response.data.result;
        } catch (error) {
            console.error('Error fetching post list:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [PUT] 글 업데이트 /qaboard/update
    const updatePost = async (data) => {
        try {
            const response = await axiosInstance.put('/qaboard/update', data);
            return response.data.result;
        } catch (error) {
            console.error('Error updating post:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    // [DELETE] 글 삭제 /qaboard/delete
    const deletePost = async (id) => {
        try {
            const response = await axiosInstance.delete('/qaboard/delete', { data: { id } });
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

            const response = await axiosInstance.post(`/qacomment/write`, formData, {
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
            const response = await axiosInstance.get(`/qacomment/search`, { params: { qaboardId } });
            return response.data.result;
        } catch (error) {
            console.error('Error fetching comments:', error);
            return error.response?.data || { message: 'Unknown error occurred' };
        }
    };

    return {
        writePost,
        getPostList,
        getPostDetail,
        updatePost,
        deletePost,
        writeComment,
        getCommentList,
    };
});
