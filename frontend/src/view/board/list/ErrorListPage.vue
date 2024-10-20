<script setup>
import { computed, inject, onMounted, ref, toRef, watch } from 'vue';
import { useErrorStore } from '@/stores/board/useErrorStore';
import Pagination from '@/common/component/PaginationComponent.vue';
import BoardList from '@/common/component/Board/BoardList.vue';
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
  currentPage.value = page;
};

const editItem = (item) => {
  console.log('Editing:', item);
};
const deleteItem = (item) => {
  console.log('Deleting:', item);
};

const errorStore = useErrorStore();
const postList = ref([]);
const selectedLanguage = toRef(route.query, 'selectedLanguage');

const fetchPostList = async () => {
  try {
    const page = Number(currentPage.value);
    if (isNaN(page) || page < 1) {
      console.error('유효하지 않은 페이지 번호:', currentPage.value);
      return;
    }

    let result;
    if (searchKeyword.value) {
      result = await errorStore.searchErrorBoardByKeyword(workspaceId, page, itemsPerPage, searchKeyword.value);
    } else {
      result = await errorStore.getErrorBoardList(workspaceId, page, itemsPerPage);
    }

    // 검색 결과가 없거나 빈 배열인 경우에도 상태 업데이트 강제
    postList.value = result && result.length ? result : [];

  } catch (error) {
    console.error('게시글 목록을 가져오는 중 오류가 발생했습니다:', error);
    postList.value = [];  // 오류 발생 시에도 postList를 빈 배열로 설정
  }
};

const filterByLanguage = async (language) => {
  try {
    const page = Number(currentPage.value);
    if (isNaN(page) || page < 1) {
      console.error('유효하지 않은 페이지 번호:', currentPage.value);
      return;
    }
    const result = await errorStore.searchErrorBoardByCategory(workspaceId, page, itemsPerPage, language);
    postList.value = result || [];
  } catch (error) {
    console.error('언어별 게시글 검색 중 오류가 발생했습니다:', error);
    postList.value = []; // 오류 발생 시에도 postList를 빈 배열로 설정
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
  currentPage.value = 1; // 검색 시 첫 페이지로 초기화
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
        />
      </div>

      <!-- 게시글 목록 -->
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

      <!-- 페이지네이션 -->
      <Pagination
        :currentPage="currentPage"
        :totalPages="totalPages"
        @prev-page="prevPage"
        @next-page="nextPage"
        @go-to-page="goToPage"
      />
    </div>

    <!-- 검색 결과가 없을 때 -->
    <div v-else>
      <div class="initial-wrap">
        <p v-if="searchKeyword">해당 키워드로 검색된 결과가 없습니다.</p>
        <p v-else>Error를 추가하고 관리를 시작해보세요!</p>
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
