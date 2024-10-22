<script setup>
import { computed, inject, onMounted, ref, watch } from 'vue';
import { useQAStore } from "@/stores/board/useQAStore";
import Pagination from '@/common/component/PaginationComponent.vue';
import BoardList from "@/common/component/Board/QABoardList.vue";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA Board List';
contentsDescription.value = 'QA 목록을 확인하세요!';

const itemsPerPage = 10;
const currentPage = ref(1);
const searchKeyword = ref('');

const totalPages = computed(() => Math.ceil((postList.value?.length || 0) / itemsPerPage));

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};
const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++;
  }
};
const goToPage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
};

const qaStore = useQAStore();
const postList = ref([]);

const fetchPostList = async () => {
  try {
    const page = Number(currentPage.value) ;
    if (isNaN(page) || page < 1) {
      console.error('유효하지 않은 페이지 번호:', currentPage.value);
      return;
    }
    if (searchKeyword.value) {
      const result = await qaStore.searchQaBoardByKeyword(workspaceId, page -1, itemsPerPage, searchKeyword.value);
      postList.value = result || [];
    }  else {
      await qaStore.getPostList(workspaceId, page, itemsPerPage);
      postList.value = qaStore.postList || [];
    }
  } catch (error) {
    console.error('게시글 목록을 가져오는 중 오류가 발생했습니다:', error);
  }
};


onMounted(async () => { await fetchPostList(); });
watch(currentPage, async () => { await fetchPostList(); });
watch(searchKeyword, async () => {
  currentPage.value = 1;
  await fetchPostList();
});
</script>

<template>
  <div class="board-list-container">
    <div >
      <div class="header">
        <div class="toolbar">
          <div class="filter-search">
            <div class="search">
              <input type="text" class="search-input" placeholder="Search..." v-model="searchKeyword"/>
              <span class="search-icon">🔍</span>
            </div>
          </div>
          <router-link :to="`/workspace/${workspaceId}/scrum/board/qa/create`" class="create-button">
            <span class="create-icon">+</span> Create
          </router-link>
        </div>
      </div>
      <div v-if="postList.length > 0">
        <BoardList :items="postList"/>
        <Pagination
            :currentPage="currentPage"
            :totalPages="totalPages"
            @prev-page="prevPage"
            @next-page="nextPage"
            @go-to-page="goToPage"
        />
      </div>
      <div v-else class="initial-wrap">
        <p>QA를 추가하고 관리를 시작해보세요!</p>
        <router-link :to="`/workspace/${workspaceId}/scrum/board/qa/create`">QA 추가하기</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.initial-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;

  a {
    padding: 10px 20px;
    background-color: #93AAFD;
    color: white;
    border-radius: 5px;
    text-decoration: none;

    &:hover {
      background-color: #6F8FFC;
    }
  }
}.toolbar {
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

.initial-wrap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;

  a {
    padding: 10px 20px;
    background-color: #93AAFD;
    color: white;
    border-radius: 5px;
    text-decoration: none;

    &:hover {
      background-color: #6F8FFC;
    }
  }
}
</style>