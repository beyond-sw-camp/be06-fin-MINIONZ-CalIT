<script setup>
import { defineProps, computed } from 'vue';

// Props 정의
const props = defineProps({
  currentPage: Number,
  totalPages: Number,
});

// 한 번에 보여줄 페이지 개수
const visibleRange = 5;

// visiblePages 계산: 현재 페이지에 따른 페이지 그룹을 계산
const visiblePages = computed(() => {
  const currentGroup = Math.ceil((props.currentPage + 1) / visibleRange); // 현재 페이지가 속한 그룹
  const startPage = (currentGroup - 1) * visibleRange + 1; // 시작 페이지
  const endPage = Math.min(currentGroup * visibleRange, props.totalPages); // 마지막 페이지
  return Array.from(
    { length: endPage - startPage + 1 },
    (_, i) => startPage + i
  );
});

const prevPage = computed(() => {
  const currentGroup = Math.ceil((props.currentPage + 1) / visibleRange); // 현재 페이지가 속한 그룹
  const startPage = (currentGroup - 1) * visibleRange + 1; // 시작 페이지
  return startPage - 1 - visibleRange < 0 ? 0 : startPage - 1 - visibleRange;
});

const nextPage = computed(() => {
  const currentGroup = Math.ceil((props.currentPage + 1) / visibleRange); // 현재 페이지가 속한 그룹

  const endPage = Math.min(currentGroup * visibleRange, props.totalPages); // 마지막 페이지
  return endPage >= props.totalPages ? props.totalPages - 1 : endPage;
});
</script>

<template>
  <div class="pagination">
    <button @click="$emit('prev-page', prevPage)" :disabled="currentPage === 0">
      &lt;
    </button>

    <!-- 현재 페이지 그룹 내에서 페이지 번호를 표시 -->
    <button
      v-for="page in visiblePages"
      :key="page"
      @click="$emit('go-to-page', page - 1)"
      :class="{ active: page === currentPage + 1 }"
    >
      {{ page }}
    </button>

    <button
      @click="$emit('next-page', nextPage)"
      :disabled="currentPage === totalPages - 1"
    >
      &gt;
    </button>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.pagination button {
  background: none;
  border: 1px solid #ddd;
  padding: 5px 10px;
  margin: 0 5px;
  cursor: pointer;
}

.pagination button.active {
  background-color: #007bff;
  color: white;
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}
</style>
