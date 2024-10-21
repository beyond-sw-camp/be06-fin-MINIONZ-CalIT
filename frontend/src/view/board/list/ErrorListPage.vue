<script setup>
import {computed, inject, onMounted, ref, watch} from 'vue';
import {useErrorStore} from '@/stores/board/useErrorStore';
import Pagination from '@/common/component/PaginationComponent.vue';
import BoardList from '@/common/component/Board/ErrorBoardList.vue';
import {useRoute} from 'vue-router';
import Multiselect from 'vue-multiselect';

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
    const page = Number(currentPage.value) - 1;
    if (isNaN(page) || page < 0) {
      console.error('유효하지 않은 페이지 번호:', currentPage.value);
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
    <div>
      <div class="header">
        <div class="toolbar">
          <div class="filter-search">
            <div class="filter">
              <multiselect v-model="selectedLanguage" :options="['JAVA', 'C', 'PYTHON', 'JS', 'SQL']"
                           @input="selectedLanguage = $event; $emit('update:selectedLanguage', $event)"/>
            </div>
            <div class="search">
              <input type="text" class="search-input" placeholder="Search..." v-model="searchKeyword"/>
              <span class="search-icon">🔍</span>
            </div>
          </div>

          <!-- Create 버튼 -->
          <router-link :to="`/workspace/${workspaceId}/scrum/board/error/create`" class="create-button">
            <span class="create-icon">+</span> Create
          </router-link>
        </div>
      </div>
      <div v-if="postList.length > 0">
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