<script setup>
import BoardDetail from '@/common/component/Board/QABoardDetail.vue';
import { computed, inject } from 'vue';
import { useRoute } from 'vue-router';
import { useQAStore } from '@/stores/board/useQAStore';
import DateComment from '@/view/board/component/DateComment.vue';
import CommentComponent from '@/view/board/detail/componenet/CommentComponent.vue';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 상세 보기';
contentsDescription.value = 'QA를 확인하세요!';

const qaStore = useQAStore();
const route = useRoute();
const boardId = route.params.boardId;

const postDetail = computed(() => qaStore.postDetail);

const fetchQaDetail = async () => {
  const postDetail = await qaStore.getPostDetail(boardId);
  contentsTitle.value = postDetail.title || 'QA 상세 보기';
  contentsDescription.value = postDetail.description || 'QA를 확인하세요!';
};

fetchQaDetail();
</script>

<template>
  <div class="qa-detail-container">
    <div>
      <BoardDetail
        :title="postDetail.qaboardTitle || ''"
        :descriptionList="[postDetail.qaboardContent || '']"
        :status="postDetail.answerStatus || ''"
        :owner="postDetail.assignUser || ''"
        :taskName="postDetail.taskName || ''"
      />
      <DateComment
        :date="postDetail.createdAt"
        :comments="postDetail.comments"
      />
    </div>
    <CommentComponent />
  </div>
</template>

<style scoped>
.qa-detail-container {
  padding: 30px;
}
</style>
