<script setup>
import {computed, inject, ref} from 'vue';
import {mySprintData} from "@/static/mySprintData";
// import { useSprintStore} from "@/stores/workspace/scrum/useSprintStore";
// import { useWorkspaceStore} from "@/stores/workspace/space/useWorkspaceStore";
import Pagination from '@/common/component/PaginationComponent.vue';
import ScrumList from "@/common/component/Board/ScrumList.vue";
import SearchComponent from "@/common/component/SearchComponent.vue";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;
// const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
// const workspace = useWorkspaceStore();
// const sprintStore = useSprintStore();
const sprintStore = mySprintData

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value =  'Sprint List';
contentsDescription.value = 'sprint 목록을 확인하세요!';

const currentPage = ref(1);
const itemsPerPage = 10;

// const totalPages = computed(() => Math.ceil((sprintListData.value?.length || 0) / itemsPerPage));
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
</script>

<template>
  <div class="board-list-container">
    <div class="header">
      <SearchComponent :link="`/workspace/${workspaceId}/scrum/sprint/create`" />
    </div>
    <!--    <BoardList :items="sprintStore.sprints" thcolumn="스프린트 명" column="sprintTitle" board-type="sprint" @edit-item="editItem" @delete-item="deleteItem" />-->
    <ScrumList
        :items="sprintStore"
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