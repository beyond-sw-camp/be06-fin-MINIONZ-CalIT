<script setup>
import {computed, inject, onMounted, ref} from 'vue';
import { useSprintStore} from "@/stores/scrum/useSprintStore";
import Pagination from '@/common/component/PaginationComponent.vue';
import ScrumList from "@/common/component/Board/ScrumList.vue";
import {useRoute} from "vue-router";

const sprintStore = useSprintStore();
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value =  'Sprint List';
contentsDescription.value = 'sprint 목록을 확인하세요!';

const route = useRoute();
const workSpaceId = route.params.workspaceId;

const currentPage = ref(1);
const itemsPerPage = 10;

const totalPages = computed(() => Math.ceil((sprintStore?.length || 0) / itemsPerPage));
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
  sprintStore.getSprintList(workSpaceId);
});
</script>

<template>
  <div class="board-list-container">
    <ScrumList
        :items="sprintStore.sprints"
        firstColumn="스프린트 명"
        secondColumn="label"
        thirdColumn="status"
        fourthColumn="priority"
        fifthColumn="taskNumber"
        board-type="sprint"/>
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