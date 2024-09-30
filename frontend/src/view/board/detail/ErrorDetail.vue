<script setup>
import BoardDetail from "@/common/component/Board/BoardDetail.vue";
import {inject} from "vue";
import DateComment from "@/view/board/component/DateComment.vue";
import CommentComponent from "@/view/board/detail/componenet/CommentComponent.vue";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Error 상세 보기';
contentsDescription.value = 'Error를 확인하세요!';
</script>

<template>
  <div class="error-detail-container">
    <div>
      <BoardDetail
          title="프론트엔드 오류"
          language="JavaScript"
          subheading="필터링 후 선택 기능 오류"
          :descriptionList="[
        '사용자가 검색할 때, 필터를 통해 검색한 내용을 거른 후 검색할 수 있어야 합니다.',
        '셀렉트 박스를 통해 필터를 누르고 나면, 하위 아이템들이 필터 된 것들로 제공되어야 합니다.'
      ]"
          owner="최승은"
      />
      <DateComment
          date="Aug 19, 2021"
          :comments="3"
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