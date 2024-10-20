<script setup>
import { computed, inject, onMounted, ref } from 'vue';
import { useSprintStore } from '@/stores/scrum/useSprintStore';
import Pagination from '@/common/component/PaginationComponent.vue';
import SprintList from '@/common/component/Board/SprintList.vue';
import { useRoute } from 'vue-router';
import SearchComponent from '@/common/component/SearchComponent.vue';

const sprintStore = useSprintStore();
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Sprint List';
contentsDescription.value = 'sprint 목록을 확인하세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const currentPage = ref(1);
const itemsPerPage = 10;
const isLoading = ref(true);

const totalPages = computed(() =>
  Math.ceil((sprintStore?.length || 0) / itemsPerPage)
);
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

onMounted(async () => {
  isLoading.value = true;
  await sprintStore.getSprintList(workspaceId);
  isLoading.value = false;
});
</script>

<template>
  <div class="board-list-container">
    <div v-if="isLoading">
      <p>데이터를 불러오는 중입니다...</p>
    </div>
    <div v-else-if="sprintStore.sprints && sprintStore.sprints.length > 0">
      <SearchComponent
        :link="`/workspace/${workspaceId}/scrum/sprint/create`"
      />
      <SprintList :items="sprintStore.sprints" board-type="sprint" />
      <Pagination
        :currentPage="currentPage"
        :totalPages="totalPages"
        @prev-page="prevPage"
        @next-page="nextPage"
        @go-to-page="goToPage"
      />
    </div>

    <div v-else class="initial-wrap">
      <p>스프린트를 추가하고 스크럼 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/${workspaceId}/scrum/sprint/create`"
        >스프린트 추가하기</router-link
      >
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
    background-color: #93aafd;
    color: white;
    border-radius: 5px;
    text-decoration: none;

    &:hover {
      background-color: #6f8ffc;
    }
  }
}
</style>
