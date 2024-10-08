import { axiosInstance } from "@/utils/axiosInstance";
import { defineStore } from "pinia";

export const useErrorStore = defineStore('errorStore', async () => {

    // [POST] 글 작성 /errboard/write?workspaceId=1
    const writePost = async ({ workspaceId, files, errboardTitle, taskId, errboardContent, category }) => {
        try {
            const response = await axiosInstance.post(`/errboard/write?workspaceId=${workspaceId}`, { files, errboardTitle, taskId, errboardContent, category });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [GET] 글 목록 조회 /errboard/search-all
    const getPostList = async ({page, size}) => {
        try {
            const response = await axiosInstance.get('/errboard/search-all', { params: { page, size } });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    }

    // [GET] 글 상세 조회 /errboard/search
    const getPostDetail = async (boardId) => {
        try {
            const response = await axiosInstance.get(`/errboard/detail`, { params: { boardId } });
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    const updatePost = async (data) => {
        try {
            const response = await axiosInstance.post('/errorboard/update', data);
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    const deletePost = async (id) => {
        try {
            const response = await axiosInstance.post('/errorboard/delete', {id});
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    };

    // [POST] 댓글 작성 errcomment/write?errorBoardId=1
    const writeComment = async (data) => {
        try {
            const response = await axiosInstance.post(`/errcomment/write?errorBoardId=${data.errorBoardId}`, data);
            return response.data.result;
        } catch (error) {
            return error.response.data;
        }
    }

    // [GET]  댓글 조회 /errcomment/search
    const getCommentList = async (errorBoardId) => {
        try {
            const response = await axiosInstance.get(`/errcomment/search`, errorBoardId);
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