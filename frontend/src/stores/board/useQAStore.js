import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";

export const useQAStore = defineStore('qaStore', () => {

    // [POST] 글 작성 /qaboard/write
    const writePost = async ({ files, qaboardTitle, qaboardContent, taskId, workspaceParticipantsId }) => {
        try {
            const response = await axiosInstance.post('/qaboard/write', { files, qaboardTitle, qaboardContent, taskId, workspaceParticipantsId });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [GET] 글 상세 조회 /qaboard/detail
    const getPostDetail = async (boardId) => {
        try {
            const response = await axiosInstance.get(`/qaboard/search`, { params: { boardId } });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [GET] 글 목록 조회 /qaboard/list
    const getPostList = async () => {
        try {
            const response = await axiosInstance.get('/qaboard/search-all');
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    const updatePost = async (data) => {
        try {
            const response = await axiosInstance.post('/qaboard/update', data);
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    const deletePost = async (id) => {
        try {
            const response = await axiosInstance.post('/qaboard/delete', { id });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [POST] 댓글 작성 /qacomment/write
    const writeComment = async ({ files, qaboardTitle, qaboardContent, progressStatus }) => {
        try {
            const response = await axiosInstance.post(`/qacomment/write`, { files, qaboardTitle, qaboardContent, progressStatus });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [GET] 댓글 조회 /qacomment/search
    const getCommentList = async (qaboardId) => {
        try {
            const response = await axiosInstance.get(`/qacomment/search`, { params: { qaboardId } });
            return response.data.result;
        } catch (error) {
            return error.response.data;
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