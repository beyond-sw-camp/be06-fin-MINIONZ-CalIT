<script setup>
import BoardDetail from '@/common/component/Board/QABoardDetail.vue';
import { ref, computed, inject } from 'vue';
import { useRoute } from 'vue-router';
import { useQAStore } from '@/stores/board/useQAStore';
import DateComment from '@/view/board/component/DateComment.vue';
import QACommentComponent from './componenet/QACommentComponent.vue'; // QA 댓글 컴포넌트 임포트

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 상세 보기';
contentsDescription.value = 'QA를 확인하세요!';

const qaStore = useQAStore();
const route = useRoute();
const boardId = route.params.boardId;

const postDetail = computed(() => qaStore.postDetail);
const loading = ref(true); // 로딩 상태

const fetchQaDetail = async () => {
  const detail = await qaStore.getPostDetail(boardId);
  contentsTitle.value = detail.qaboardTitle || 'QA 상세 보기';
  contentsDescription.value = detail.qaboardContent || 'QA를 확인하세요!';
  loading.value = false; // 로딩 완료
};

fetchQaDetail();
</script>

<template>
  <div v-if="loading" class="loading-indicator">
    <p>로딩 중...</p>
  </div>
  <div v-else class="qa-detail-container">
    <div>
      <BoardDetail
        :title="postDetail.qaboardTitle || ''"
        :descriptionList="[postDetail.qaboardContent || '']"
        :status="postDetail.answerStatus || ''"
        :owner="postDetail.assignUser || ''"
        :taskName="postDetail.taskName || ''"
      />
      
      <!-- 이미지 목록을 렌더링하는 부분 -->
      <div v-if="postDetail.getQaBoardImageResponseList && postDetail.getQaBoardImageResponseList.length">
        <h3>첨부 파일:</h3>
        <div v-for="image in postDetail.getQaBoardImageResponseList" :key="image.qaBoardImageId" class="image-container">
          <img :src="image.imageUrl" alt="게시글 이미지" class="qa-image" />
        </div>
      </div>

      <!-- DateComment는 댓글 작성 날짜를 표시할 뿐이므로 댓글 목록은 QACommentComponent에서 처리 -->
      <DateComment :date="postDetail.createdAt" />

      <!-- QA 댓글 컴포넌트 사용 -->
      <QACommentComponent :qaBoardId="boardId" />
    </div>
  </div>
</template>

<style scoped>
.qa-detail-container {
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
.qa-image {
  max-width: 100%;
  height: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
}
</style>
