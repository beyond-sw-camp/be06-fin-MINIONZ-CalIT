<script setup>
import {inject, onMounted} from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import PriorityTask from "@/view/dashboard/component/PriorityTask.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import {useMypageStore} from "@/stores/my/useMypageStore";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Dashboard';
contentsDescription.value = '나의 할 일을 살펴보세요! ';

const mypageStore = useMypageStore();

onMounted(async () => {
  await mypageStore.getMyDashboard();
});
</script>

<template>
  <div class="dashboard">
    <TaskOverview
        v-if="mypageStore.mySprintData && mypageStore.mySprintData.progress && mypageStore.mySprintData.progress.successTaskCount !== undefined && mypageStore.mySprintData.progress.allTaskCount !== undefined"
        :completion-rate="mypageStore.mySprintData.progress.successTaskCount / mypageStore.mySprintData.progress.allTaskCount * 100"
        :tasks-completed="mypageStore.mySprintData.progress.successTaskCount"
        :total-tasks="mypageStore.mySprintData.progress.allTaskCount"
        :work-space-count="mypageStore.mySprintData.progress.workspaceCount"/>
    <PriorityTask :tasks="mypageStore.mySprintData.tasks"/>
    <MeetingList :meetings="mypageStore.mySprintData.meetings"/>
  </div>
</template>

<style scoped>
.dashboard {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>