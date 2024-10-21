<script setup>
import { defineProps } from 'vue';
defineProps({
  currentPage: Number,
  totalPages: Number,
});
</script>

<template>
  <div class="pagination">
    <button @click="$emit('prev-page')">
      &lt;
    </button>
    <button
        v-for="page in totalPages"
        :key="page"
        @click="$emit('go-to-page', page)"
        :class="{ active: page === currentPage }"
    >
      {{ page }}
    </button>
    <button @click="$emit('next-page')">
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
</style>