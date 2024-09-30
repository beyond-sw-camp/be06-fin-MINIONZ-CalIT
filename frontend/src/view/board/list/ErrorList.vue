<script setup>
import {computed, inject, ref} from 'vue';
import { errorListData } from "@/static/errorListData";
// import { useWorkspaceStore} from "@/stores/workspace/space/useWorkspaceStore";
import Pagination from '@/common/component/PaginationComponent.vue';
import BoardList from "@/common/component/Board/BoardList.vue";
import SearchComponent from "@/common/component/SearchComponent.vue";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;
// const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
// const workspace = useWorkspaceStore();

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Error List';
contentsDescription.value = 'Error 목록을 확인하세요!';

const currentPage = ref(1);
const itemsPerPage = 10;

const totalPages = computed(() => Math.ceil((errorListData.value?.length || 0) / itemsPerPage));

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
</script>

<template>
  <div class="board-list-container">
    <div class="header">
      <SearchComponent :link="`/workspace/${workspaceId}/scrum/board/error/create`" />
    </div>
    <BoardList :items="errorListData" thcolumn="언어" column="language" board-type="error" @edit-item="editItem" @delete-item="deleteItem" />
    <Pagination
        :currentPage="currentPage"
        :totalPages="totalPages"
        @prev-page="prevPage"
        @next-page="nextPage"
        @go-to-page="goToPage"
    />
  </div>
</template>

<style scoped>

</style>