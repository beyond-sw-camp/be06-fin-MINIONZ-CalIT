<script setup>
import {inject, onMounted, ref, watch} from 'vue';
import WorkspaceTaskOverview from '@/view/dashboard/component/WorkspaceTaskOverview.vue';
import MeetingList from '@/view/dashboard/component/MeetingList.vue';
import BurndownChart from '@/view/dashboard/component/BurndownChart.vue';
import {useWorkspaceDashboardStore} from '@/stores/workspace/useWorkspaceDashboardStore';
import {useWorkspaceStore} from '@/stores/workspace/useWorkspaceStore';
import {useRoute} from 'vue-router';

const route = useRoute();
const workspaceStore = useWorkspaceStore();
const dashboardStore = useWorkspaceDashboardStore();

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Workspace Dashboard';
contentsDescription.value = '워크스페이스의 대시보드를 살펴보세요!';

const dashboard = ref(null);

watch(() => route.query.wsId, (newId) => {
  workspaceStore.setWorkspaceId(newId);
  fetchDashboardData(newId);
});

const fetchDashboardData = async (workspaceId) => {
  if (workspaceId) {
    try {
      dashboard.value = await dashboardStore.getWorkspaceDashboard(workspaceId);
    } catch (error) {
      console.error('Failed to fetch workspace data', error);
    }
  } else {
    console.error('workspaceId is not set');
  }
};

onMounted(() => {
  if (route.query.wsId) {
    workspaceStore.setWorkspaceId(route.query.wsId);
    fetchDashboardData(route.query.wsId);
  }
});
</script>

<template>
  <div class="dashboard">
    <div v-if="dashboard && dashboard.progress.allSprintCount > 0">
      <WorkspaceTaskOverview
          :sprint-count="dashboard.progress.allSprintCount"
          :completion-rate="
          dashboard.progress.allTaskCount === 0
            ? 0
            : (dashboard.progress.successTaskCount /
                dashboard.progress.allTaskCount) *
              100
        "
          :tasks-completed="dashboard.progress.successTaskCount"
          :total-tasks="dashboard.progress.allTaskCount"
          :issue-count="dashboard.progress.issueCount"
      />
      <BurndownChart/>
      <MeetingList
          v-if="dashboard.upcomingMeetings"
          :meetings="dashboard.upcomingMeetings"
      />
    </div>
    <div v-else class="initial-wrap">
      <p>워크스페이스와 스크럼을 추가하고 스크럼 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/scrum/sprint/create?wsId=${workspaceStore.workspaceId}`">
        스프린트 추가하기
      </router-link>
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