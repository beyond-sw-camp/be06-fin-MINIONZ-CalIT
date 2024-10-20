<script setup>
import { computed, defineProps } from 'vue';
import { formatDate } from '@/utils/timeUtils';

const props = defineProps({
  date: String,
  comments: Number,
});

const formattedDate = computed(() => {
  return props.date;
});
</script>

<template>
  <div class="date-comment">
    <span class="date">{{ formatDate(formattedDate) }}</span>
    <div class="comments">
      <i class="comment-icon"></i>
      <span class="comment-count">{{ comments }}</span>
    </div>
  </div>
</template>

<style scoped>
.date-comment {
  display: flex;
  align-items: center;
  font-size: 14px;
  justify-content: space-between;
  margin-top: 20px;
  padding: 0 10px;
}

.date {
  margin-right: 10px;
}

.comments {
  display: flex;
  align-items: center;
}

.comment-icon {
  background-image: url('@/assets/icon/boardIcon/boardComment.svg');
  width: 24px;
  height: 24px;
  margin-right: 5px;
}

.comment-count {
  font-size: 14px;
}
</style>
