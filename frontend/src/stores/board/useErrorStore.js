import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";
import { ref } from "vue";

export const useErrorStore = defineStore('errorStore', () => {
    const posts = ref([]);

    // [POST] 글 작성 /errboard/write?workspaceId=1
    const writePost = async ({ workspaceId, files, errboardTitle, errboardContent, taskId, category }) => {
        try {
            const response = await axiosInstance.post(`/api/errboard/write?workspaceId=${workspaceId}`, { files, errboardTitle, errboardContent, taskId, category });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };
    // [GET] 글 목록 조회 /errboard/search-all
    const getPostList = async ({page, size}) => {
        try {
            const response = await axiosInstance.get('/api/errboard/search-all', { params: { page, size } });
            posts.value = response.data.result;
        } catch (error) {
            posts.value = [];
        }
    };

    // [GET] 글 상세 조회 /errboard/search
    const getPostDetail = async (boardId) => {
        try {
            const response = await axiosInstance.get(`/api/errboard/detail`, { params: { boardId } });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    const updatePost = async (data) => {
        try {
            const response = await axiosInstance.post('/api/errboard/update', data);
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    const deletePost = async (id) => {
        try {
            const response = await axiosInstance.post('/api/errboard/delete', {id});
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [POST] 댓글 작성 errcomment/write?errorBoardId=1
    const writeComment = async (data) => {
        try {
            const response = await axiosInstance.post(`/api/errcomment/write?errorBoardId=${data.errorBoardId}`, data);
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    }

    // [GET]  댓글 조회 /errcomment/search
    const getCommentList = async (errorBoardId) => {
        try {
            const response = await axiosInstance.get(`/api/errcomment/search`, { params: { errorBoardId } });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    }

    return {
        writePost,
        getPostList,
        getPostDetail,
        updatePost,
        deletePost,
        writeComment,
        getCommentList
    };
});