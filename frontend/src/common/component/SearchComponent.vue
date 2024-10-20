<script setup>
import { defineProps, defineEmits, onMounted, ref, watch } from 'vue';
import Multiselect from 'vue-multiselect';
import { useErrorStore } from '@/stores/board/useErrorStore'; // 에러 게시판 스토어
import 'vue-multiselect/dist/vue-multiselect.min.css';
import { useRoute } from 'vue-router';

const selectedLanguage = ref('');
const searchKeyword = ref('');
const errorStore = useErrorStore();
const route = useRoute();
const workspaceId = ref(route.params.workspaceId); // URL에서 workspaceId 가져오기

defineProps({
  link: String,
});

defineEmits(['update:selectedLanguage']);

// 현재 페이지가 Error 리스트 페이지인지 확인
const isErrorPage = ref(false);

onMounted(() => {
  if (window.location.href.endsWith('/error/list')) {
    isErrorPage.value = true;
  }
});

// 언어 또는 검색어가 변경될 때 API 호출
watch([selectedLanguage, searchKeyword], async () => {
  if (isErrorPage.value && workspaceId.value) {
    const page = 0; // 첫 페이지로 설정
    const size = 10; // 페이지당 게시글 수

    if (selectedLanguage.value) {
      // 언어 필터로 게시글 검색
      await errorStore.searchErrorBoardByCategory(workspaceId.value, page, size, selectedLanguage.value);
    }

    if (searchKeyword.value.trim() !== '') {
      // 검색어로 게시글 검색
      await errorStore.searchErrorBoardByKeyword(workspaceId.value, page, size, searchKeyword.value);
    }
  }
});
</script>



<template>
  <div class="toolbar">
    <div class="filter-search">
      <div v-if="isErrorPage" class="filter">
        <multiselect v-model="selectedLanguage" :options="['JAVA', 'C', 'PYTHON', 'JS', 'SQL']" @input="$emit('update:selectedLanguage', selectedLanguage)" />
      </div>
      <div class="search">
        <input type="text" class="search-input" placeholder="Search..." />
        <span class="search-icon">🔍</span>
      </div>
    </div>

    <!-- Create 버튼 -->
    <router-link :to="link" class="create-button">
      <span class="create-icon">+</span> Create
    </router-link>
  </div>
</template>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
}

.filter-search {
  display: flex;
  align-items: center;
}

.filter-button {
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background-color: #f8f8f8;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.dropdown-icon {
  margin-left: 5px;
}

.search {
  display: flex;
  align-items: center;
  position: relative;
}

.search-input {
  padding: 8px 8px 8px 30px;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  width: 200px;
  background-color: #f8f8f8;
}

.search-icon {
  position: absolute;
  margin-left: 10px;
}

.create-button {
  text-decoration: none;
  background-color: #e0e8ff;
  color: #666daf;
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  align-items: center;
  font-weight: bold;
}

.create-icon {
  margin-right: 5px;
}
</style>