<script setup>
import {computed, inject} from 'vue';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import BurndownChart from "@/view/dashboard/component/BurndownChart.vue";

import { workspaceStore } from "@/stores/workspace/space/useWorkspaceStore";
import {workspaceData} from "@/static/workspaceData";

const workspaceId = workspaceStore.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = workspace.value ? `${workspaceStore.setWorkspaceName(workspace)} Dashboard` : 'Dashboard';
contentsDescription.value = '워크스페이스의 할일을 살펴보세요!';
</script>

<template>
  <div class="dashboard">
    <TaskOverview />
    <BurndownChart />
    <MeetingList />
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