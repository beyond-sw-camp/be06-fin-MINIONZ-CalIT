<script setup>
import BoardDetail from "@/common/component/Board/BoardDetail.vue";
import {inject} from "vue";
import { useQAStore} from "@/stores/board/useQAStore";
import DateComment from "@/view/board/component/DateComment.vue";
import CommentComponent from "@/view/board/detail/componenet/CommentComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA 상세 보기';
contentsDescription.value = 'QA를 확인하세요!';

const qaStore = useQAStore();
</script>

<template>
  <div class="qa-detail-container">
    <div>
      <BoardDetail
          :title="qaStore.postDetail.title"
          :status="qaStore.postDetail.status"
          :subheading="qaStore.postDetail.subheading"
          :descriptionList="qaStore.postDetail.descriptionList"
          :owner="qaStore.postDetail.owner"
      />
      <DateComment
          :date="qaStore.postDetail.date"
          :comments="qaStore.postDetail.comments"
      />
    </div>
    <CommentComponent/>
  </div>
</template>

<style scoped>
.qa-detail-container{
  padding: 30px;
}
</style>