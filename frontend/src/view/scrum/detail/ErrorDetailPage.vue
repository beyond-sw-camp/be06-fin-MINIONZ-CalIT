<script setup>
import BoardDetail from "@/common/component/Board/ErrorBoardDetail.vue";
import {inject} from "vue";
import { useRoute } from 'vue-router';
import { useErrorStore } from "@/stores/board/useErrorStore";
import DateComment from "@/view/board/component/DateComment.vue";
import CommentComponent from "@/view/board/detail/componenet/CommentComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Error 상세 보기';
contentsDescription.value = 'Error를 확인하세요!';

const errorStore = useErrorStore();
const route = useRoute();
const boardId = route.params.boardId;

const fetchErrorDetail = async () => {
  const postDetail = await errorStore.getPostDetail(boardId);
  contentsTitle.value = postDetail.title || 'Error 상세 보기';
  contentsDescription.value = postDetail.description || 'Error를 확인하세요!';
};

fetchErrorDetail();
</script>

<template>
  <div class="error-detail-container">
    <div>
      <BoardDetail
          :title="errorStore.postDetail.title"
          :language="errorStore.postDetail.language"
          :subheading="errorStore.postDetail.subheading"
          :descriptionList="errorStore.postDetail.descriptionList"
          :owner="errorStore.postDetail.owner"
      />
      <DateComment
          :date="errorStore.postDetail.date"
          :comments="errorStore.postDetail.comments"
      />
    </div>
    <CommentComponent/>
  </div>
</template>

<style scoped>
.error-detail-container{
  padding: 30px;
}
</style>