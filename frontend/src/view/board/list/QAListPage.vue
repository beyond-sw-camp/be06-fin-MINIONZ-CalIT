<script setup>
import { computed, inject, onMounted, ref, watch } from 'vue';
import { useQAStore } from "@/stores/board/useQAStore";
import Pagination from '@/common/component/PaginationComponent.vue';
import BoardList from "@/common/component/Board/QABoardList.vue";
import SearchComponent from "@/common/component/SearchComponent.vue";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'QA Board List';
contentsDescription.value = 'QA 목록을 확인하세요!';

const currentPage = ref(1);
const itemsPerPage = 10;
const searchKeyword = ref('');

const totalPages = computed(() => Math.ceil(( postList.value?.length || 0) / itemsPerPage));
const prevPage = () => { if (currentPage.value > 1) { currentPage.value--; }};
const nextPage = () => { if (currentPage.value < totalPages.value) { currentPage.value++; }}
const goToPage = (page) => { currentPage.value = page };

// const editItem = (item) => { console.log('Editing:', item)};
// const deleteItem = (item) => { console.log('Deleting:', item)};

const qaStore = useQAStore();
const postList = ref([]);

const fetchPostList = async () => {
  try {
    const page = Number(currentPage.value);
    if (isNaN(page) || page < 1) {
      console.error('유효하지 않은 페이지 번호:', currentPage.value);
      return;
    }

    if (searchKeyword.value) {
      const result = await qaStore.searchQaBoardByKeyword({workspaceId, page, itemsPerPage, searchKeyword});
      postList.value = result || [];
    } else {
      await qaStore.getPostList(workspaceId, page, itemsPerPage);
      postList.value = qaStore.postList || [];
    }
  } catch (error) {
    console.error('게시글 목록을 가져오는 중 오류가 발생했습니다:', error);
  }
}
onMounted(async () => { await fetchPostList() });
watch(currentPage, async () => { await fetchPostList() });

watch(currentPage, async () => {
  await fetchPostList();
});

watch(searchKeyword, async () => {
  currentPage.value = 1;
  await fetchPostList();
});
</script>

<template>
  <div class="board-list-container">
    <div v-if="postList.length > 0">
      <div class="header">
        <SearchComponent :link="`/workspace/${workspaceId}/scrum/board/qa/create`" />
      </div>
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
}
</style>