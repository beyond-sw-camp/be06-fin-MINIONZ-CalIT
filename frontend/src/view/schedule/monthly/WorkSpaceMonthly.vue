<script setup>
import {inject} from "vue";
import { useWorkspaceDashboardStore} from "@/stores/workspace/useWorkspaceDashboardStore";
import MonthlyComponent from "@/view/schedule/monthly/component/MonthlyComponent.vue";
import {useRoute} from "vue-router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Workspace  Monthly Schedule';
contentsDescription.value = '워크스페이스의 이달 일정을 살펴보세요!';

const dashboardStore = useWorkspaceDashboardStore();

const route = useRoute();
const workspaceId = route.params.workspaceId;
</script>

<template>
  <div class="monthly">
    <MonthlyComponent :data="dashboardStore.getWorkspaceMonthly(workspaceId)"/>
  </div>
</template>

<style scoped>
.monthly{
  padding: 20px;
}
</style>