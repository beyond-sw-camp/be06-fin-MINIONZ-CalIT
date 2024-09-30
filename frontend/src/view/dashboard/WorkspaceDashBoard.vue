<script setup>
import {computed, inject, onMounted} from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import BurndownChart from "@/view/dashboard/component/BurndownChart.vue";

import { useWorkspaceStore } from "@/stores/workspace/space/useWorkspaceStore";
import { useDashboardStore } from "@/stores/workspace/useDashboard";

const workspaceId = useWorkspaceStore().workspaceId;
const dashboardStore = useDashboardStore();
const workspace = computed(() => workspaceId);
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = workspace.value ? `${useWorkspaceStore().workspaceName} Dashboard` : 'Dashboard';
contentsDescription.value = '워크스페이스의 할일을 살펴보세요!';

onMounted(async () => {
  await dashboardStore.getWorkspaceDashboard(workspaceId);
});
</script>

<template>
  <div class="dashboard">
    <TaskOverview
        v-if="workspace.value && workspace.value.progress"
        :completion-rate="workspace.value.progress.successtaskCount / workspace.value.progress.alltaskCount * 100"
        :tasks-completed="workspace.value.progress.successtaskCount"
        :total-tasks="workspace.value.progress.alltaskCount"
        :work-space-count="workspace.value.progress.sprintCount"/>
    <BurndownChart />
    <MeetingList :meetings="workspace.value.upcomingMeetings"/>
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