<script setup>
import { ref, inject, onMounted } from 'vue';
import BoardDetail from '@/common/component/Board/ErrorBoardDetail.vue';
import { useRoute } from 'vue-router';
import { useErrorStore } from '@/stores/board/useErrorStore';
import DateComment from '@/view/board/component/DateComment.vue';
import CommentComponent from '@/view/board/detail/componenet/CommentComponent.vue';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Error 상세 보기';
contentsDescription.value = 'Error를 확인하세요!';

const errorStore = useErrorStore();
const route = useRoute();
const boardId = route.params.boardId;
const postDetail = ref(null);
const loading = ref(true); // 로딩 상태 추가

const fetchErrorDetail = async () => {
  const detail = await errorStore.getErrorBoard(boardId);
  postDetail.value = detail;
  contentsTitle.value = detail.title || 'Error 상세 보기';
  contentsDescription.value = detail.description || 'Error를 확인하세요!';
  loading.value = false; // 로딩 완료
};

onMounted(fetchErrorDetail);
</script>

<template>
  <div v-if="loading" class="loading-indicator">
    <p>로딩 중...</p>
  </div>
  <div v-else class="error-detail-container">
    <div>
      <BoardDetail
        :title="postDetail.errboardTitle"
        :status="postDetail.errboardCategory"
        :subheading="postDetail.errboardContent"
        :descriptionList="postDetail.getErrorBoardImageResponsesList"
        :owner="postDetail.userName"
        :taskName="postDetail.taskName"
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
.error-detail-container {
  padding: 30px;
}
.loading-indicator {
  text-align: center;
  padding: 50px;
  font-size: 18px;
}
</style>
