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
        :owner="postDetail.userName"
        :taskName="postDetail.taskName"
      />
      
      <!-- 이미지 목록을 렌더링하는 부분 -->
      <div v-if="postDetail.getErrorBoardImageResponsesList && postDetail.getErrorBoardImageResponsesList.length">
        <h3>첨부 파일:</h3>
        <div v-for="image in postDetail.getErrorBoardImageResponsesList" :key="image.errorBoardImageId" class="image-container">
          <img :src="image.imageUrl" alt="게시글 이미지" class="error-image" />
        </div>
      </div>

      <DateComment
        :date="postDetail.createdAt"
        :comments="postDetail.comments"
      />
    </div>
    <CommentComponent />
  </div>
</template>

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

<style scoped>
.error-detail-container {
  padding: 30px;
}
.loading-indicator {
  text-align: center;
  padding: 50px;
  font-size: 18px;
}
.image-container {
  margin-top: 15px;
}
.error-image {
  max-width: 100%;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>
