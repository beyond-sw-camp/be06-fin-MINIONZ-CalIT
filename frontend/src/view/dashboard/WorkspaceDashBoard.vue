<script setup>
import {computed, inject} from 'vue';
import { useRoute } from 'vue-router';
import TaskOverview from "@/view/dashboard/component/TaskOverview.vue";
import MeetingList from "@/view/dashboard/component/MeetingList.vue";
import BurndownChart from "@/view/dashboard/component/BurndownChart.vue";

import { workspaceData } from "@/static/workspaceData";

const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = workspace.value ? `${workspace.value.workspaceName} Dashboard` : 'Dashboard';
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