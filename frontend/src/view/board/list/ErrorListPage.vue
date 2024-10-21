<script setup>
import { computed, inject, onMounted, ref,  watch } from 'vue';
import { useErrorStore } from '@/stores/board/useErrorStore';
import Pagination from '@/common/component/PaginationComponent.vue';
import BoardList from '@/common/component/Board/ErrorBoardList.vue';
import SearchComponent from '@/common/component/SearchComponent.vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Error List';
contentsDescription.value = 'Error 목록을 확인하세요!';

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
  currentPage.value = page
};

const editItem = (item) => {
  console.log('Editing:', item)
};
const deleteItem = (item) => {
  console.log('Deleting:', item)
};

const errorStore = useErrorStore();
const postList = ref([]);
const selectedLanguage = ref('');

const fetchPostList = async () => {
  try {
    const page = Number(currentPage.value);
    if (isNaN(page) || page < 1) {
      console.error('유효하지 않은 페이지 번호:', currentPage.value);
      return;
    }
    if (searchKeyword.value) {
      const result = await errorStore.searchErrorBoardByKeyword(workspaceId, page, itemsPerPage, searchKeyword.value);
      postList.value = result || [];
    } else if (selectedLanguage.value) {
      const result = await errorStore.searchErrorBoardByCategory(workspaceId, page, itemsPerPage, selectedLanguage.value);
      postList.value = result || [];
    } else {
      await errorStore.getErrorBoardList(workspaceId, page, itemsPerPage);
      postList.value = errorStore.errorBoards || [];
    }
  } catch (error) {
    console.error('게시글 목록을 가져오는 중 오류가 발생했습니다:', error);
  }
};

const filterByLanguage = async (language) => {
  try {
    const page = Number(currentPage.value);
    if (isNaN(page) || page < 1) {
      console.error('유효하지 않은 페이지 번��:', currentPage.value);
      return;
    }
    const result = await errorStore.searchErrorBoardByCategory(workspaceId, page, itemsPerPage, language);
    postList.value = result || [];
  } catch (error) {
    console.error('언어별 게시글 검색 중 오류가 발생했습니다:', error);
  }
};

watch(selectedLanguage, async (newLanguage) => {
  await filterByLanguage(newLanguage);
});

onMounted(async () => {
  if (window.location.href.endsWith('/error/list')) {
    await fetchPostList();
  }
});

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
        <SearchComponent
            :link="`/workspace/${workspaceId}/scrum/board/error/create`"
            @search="searchKeyword = $event"
            @update:selectedLanguage="filterByLanguage"
        />
      </div>
      <BoardList
          :items="postList"
          thcolumn="언어"
          column="language"
          board-type="error"
          @edit-item="editItem"
          @delete-item="deleteItem"
          @search="searchKeyword = $event"
          @filter="filterByLanguage($event)"
      />
      <Pagination
          :currentPage="currentPage"
          :totalPages="totalPages"
          @prev-page="prevPage"
          @next-page="nextPage"
          @go-to-page="goToPage"
      />
    </div>
    <div v-else>
      <div class="initial-wrap">
        <p>Error를 추가하고 관리를 시작해보세요!</p>
        <router-link :to="`/workspace/${workspaceId}/scrum/board/error/create`">Error 추가하기</router-link>
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
}
</style>