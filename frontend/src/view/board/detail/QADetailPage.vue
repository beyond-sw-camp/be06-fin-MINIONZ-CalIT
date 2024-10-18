<script setup>
import { computed, inject } from "vue";
import { useRoute } from 'vue-router';
import { useQAStore } from "@/stores/board/useQAStore";
import DateComment from "@/view/board/component/DateComment.vue";
import CommentComponent from "@/view/board/detail/componenet/CommentComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 상세 보기';
contentsDescription.value = 'QA를 확인하세요!';

const qaStore = useQAStore();
const route = useRoute();
const boardId = route.params.boardId;

const statusClass = computed(() => {
  return postDetail.value.answerStatus === 'In Progress' ? 'in-progress' : 'done';
});

const postDetail = computed(() => qaStore.postDetail);

const fetchQaDetail = async () => {
  const result = await qaStore.getPostDetail(boardId);
  contentsTitle.value = result.qaboardTitle || 'QA 상세 보기';
  contentsDescription.value = result.qaboardContent || 'QA를 확인하세요!';
  postDetail.value = result;
};

fetchQaDetail();
</script>

<template>
  <div class="qa-detail-container">
    <div>
      <div>
        <div class="task-card">
          <div class="header">
            <p class="title">{{ postDetail.value.qaboardTitle }}</p>
            <span class="status" :class="statusClass">{{ postDetail.value.answerStatus }}</span>
          </div>
          <div class="description">
            <p class="sub-heading">{{ postDetail.value.taskName }}</p>
            <ul>
              <li v-for="(item, index) in postDetail.value.getQaBoardImageResponseList" :key="index" class="description-content">{{ item }}</li>
            </ul>
          </div>
          <div class="footer">
            <p><strong>담당자:</strong> {{ postDetail.value.assignUser }}</p>
          </div>
        </div>
      </div>
      <DateComment
          :date="postDetail.value.createdAt"
          :comments="postDetail.value.comments"
      />
    </div>
    <CommentComponent/>
  </div>
</template>

<style scoped>
.qa-detail-container{
  padding: 30px;
}

.task-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  width: 100%;
  background-color: white;
  box-sizing: border-box;
  min-height: 400px;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-size: 24px;
  font-weight: 500;
  margin: 0;
}

.sub-heading {
  font-size: 16px;
  font-weight: 500;
  margin: 0;
}

.description-content{
  font-size: 14px;
  margin-top: 10px;
}

.status {
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 14px;
}

.in-progress {
  background-color: rgba(236, 72, 153, 0.1);
  color: rgb(236, 72, 153);
}

.done {
  background-color: rgba(34, 197, 94, 0.1);
  color: rgb(34, 197, 94);
}

.description {
  margin-top: 15px;
  margin-bottom: 20px;
}

.description h4 {
  margin-bottom: 10px;
}

.description ul {
  list-style: none;
  padding: 0;
}

.footer {
  margin-top: auto;
  font-size: 14px;
}
</style>