<script setup>
import {computed, inject, onMounted, ref} from 'vue';
import {useIssueStore} from "@/stores/scrum/useIssueStore";
import SearchComponent from "@/common/component/SearchComponent.vue";
import Pagination from '@/common/component/PaginationComponent.vue';
import ScrumList from "@/common/component/Board/ScrumList.vue";
import {useRoute} from "vue-router";

const issueStore = useIssueStore();
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Issue List';
contentsDescription.value = 'Issue 목록을 확인하세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const currentPage = ref(1);
const itemsPerPage = 10;

const totalPages = computed(() => Math.ceil((issueStore.issues?.length || 0) / itemsPerPage));
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

onMounted(() => {
  issueStore.getIssueList(workspaceId);
});
</script>

<template>
  <div class="board-list-container">
    <div v-if="issueStore.issues && issueStore.issues.length > 0">
      <SearchComponent/>
      <ScrumList
          :items="issueStore.issues"
          firstColumn="스프린트 명"
          secondColumn="label"
          thirdColumn="status"
          fourthColumn="priority"
          fifthColumn="taskNumber"
          board-type="issue"/>
      <Pagination
          :currentPage="currentPage"
          :totalPages="totalPages"
          @prev-page="prevPage"
          @next-page="nextPage"
          @go-to-page="goToPage"
      />
    </div>
    <div v-else class="initial-wrap">
      <p>이슈를 추가하고 스크럼 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/${workspaceId}/scrum/issue/create`">이슈 추가하기</router-link>
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