<script setup>
import {inject, onMounted} from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import PriorityTask from "@/view/dashboard/component/PriorityTask.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import {useMypageStore} from "@/stores/my/useMypageStore";
import {useWorkspaceStore} from "@/stores/workspace/space/useWorkspaceStore";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Dashboard';
contentsDescription.value = '나의 할 일을 살펴보세요!';

const mypageStore = useMypageStore();
const workspaceStore = useWorkspaceStore();

onMounted(async () => {
  await mypageStore.getMyDashboard();
});
</script>

<template>
  <div class="dashboard">
    <div v-if="workspaceStore.getAllWorkspace().length === 0">
      <TaskOverview
          v-if="mypageStore.mySprintData && mypageStore.mySprintData.progress && mypageStore.mySprintData.progress.successTaskCount
        !== undefined && mypageStore.mySprintData.progress.allTaskCount !== undefined"
          :completion-rate="mypageStore.mySprintData.progress.successTaskCount / mypageStore.mySprintData.progress.allTaskCount * 100"
          :tasks-completed="mypageStore.mySprintData.progress.successTaskCount"
          :total-tasks="mypageStore.mySprintData.progress.allTaskCount"
          :work-space-count="mypageStore.mySprintData.progress.workspaceCount"/>
      <PriorityTask :tasks="mypageStore.mySprintData.tasks"/>
      <MeetingList :meetings="mypageStore.mySprintData.meetings"/>
    </div>
    <div v-else class="initial-wrap">
      <p>워크스페이스와 스크럼을 추가하고 스크럼 관리를 시작해보세요!</p>
      <router-link to="my/create">워크스페이스 추가하기</router-link>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.initial-wrap{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 500px;
  gap: 20px;
  a{
    padding: 10px 20px;
    background-color: #93AAFD;
    color: white;
    border-radius: 5px;
    text-decoration: none;
    &:hover{
      background-color: #6F8FFC;
    }
  }
}
</style>