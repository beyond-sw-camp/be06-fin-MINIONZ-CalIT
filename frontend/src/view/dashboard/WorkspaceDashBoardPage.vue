<script setup>
import { inject, onMounted } from 'vue';
import TaskOverview from '@/view/dashboard/component/TaskOverview.vue';
import MeetingList from '@/view/dashboard/component/MeetingList.vue';
import BurndownChart from '@/view/dashboard/component/BurndownChart.vue';
import {useWorkspaceDashboardStore} from '@/stores/workspace/useWorkspaceDashboardStore';
import {useRoute} from 'vue-router';

const route = useRoute();
const workspaceId = route.params.workspaceId;
// const workspaceStore = useWorkspaceStore();
const dashboardStore = useWorkspaceDashboardStore();
// const workspace = computed(() => workspaceStore.workspace);
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Workspace Dashboard';
contentsDescription.value = '워크스페이스의 대시보드를 살펴보세요!';

onMounted(async () => {
  if (workspaceId) {
    try {
      await dashboardStore.getWorkspaceDashboard(workspaceId);
    } catch (error) {
      console.error('Failed to fetch workspace data', error);
    }
  } else {
    console.error('workspaceId is not set');
  }
});
</script>

<template>
  <div class="dashboard">
    <div
        v-if="dashboardStore.workspaceDashboardData && dashboardStore.workspaceDashboardData.progress && dashboardStore.workspaceDashboardData.progress.sprintCount > 0">
      <TaskOverview
          v-if="dashboardStore.workspaceDashboardData.progress"
          :completion-rate="dashboardStore.workspaceDashboardData.progress.successtaskCount / dashboardStore.workspaceDashboardData.progress.alltaskCount * 100"
          :tasks-completed="dashboardStore.workspaceDashboardData.progress.successtaskCount"
          :total-tasks="dashboardStore.workspaceDashboardData.progress.alltaskCount"
          :work-space-count="dashboardStore.workspaceDashboardData.progress.sprintCount"/>
      <BurndownChart/>
      <MeetingList v-if="dashboardStore.workspaceDashboardData.upcomingMeetings"
                   :meetings="dashboardStore.workspaceDashboardData.upcomingMeetings"/>
    </div>
    <div v-else class="initial-wrap">
      <p>워크스페이스와 스크럼을 추가하고 스크럼 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/${workspaceId}/scrum/sprint/create`">스프린트 추가하기</router-link>
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