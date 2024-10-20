<script setup>
import { inject, onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import MeetingCard from '@/view/scrum/meeting/component/MeetingCard.vue';
import SearchComponent from '@/common/component/SearchComponent.vue';
import { useMeetingStore } from '@/stores/scrum/useMeetingStore';
import PaginationComponent from '@/common/component/PaginationComponent.vue';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Meeting';
contentsDescription.value = '워크스페이스의 회의 내역을 살펴보세요!';

const meetingStore = useMeetingStore();

const currentPage = ref(0);
const itemsPerPage = ref(10);

const fetchMeetings = async () => {
  const page = currentPage.value;
  const size = itemsPerPage.value;
  await meetingStore.getMeetingList({ workspaceId, page, size });
};

onMounted(() => {
  fetchMeetings();
});
</script>

<template>
  <div class="meeting-container">
    <div v-if="meetingStore.meetings && meetingStore.meetings.length > 0">
      <SearchComponent
        :link="`/workspace/${workspaceId}/scrum/meeting/create`"
      />
      <div class="meeting-card-container">
        <MeetingCard
          v-for="meeting in meetingStore.meetings"
          :key="meeting.id"
          :id="meeting.id"
          :title="meeting.title"
          :labels="meeting.labels"
          :contents="meeting.contents"
          :participants="meeting.participants?.map((participant) => participant.persona) || []"
          :startDate="meeting.startDate"
        />
        <PaginationComponent
          :currentPage="currentPage"
          :itemsPerPage="itemsPerPage"
          :totalItems="10"
          @pageChanged="fetchMeetings"
        />
      </div>
    </div>
    <div v-else class="initial-wrap">
      <p>회의를 추가하고 일정 관리를 시작해보세요!</p>
      <router-link :to="`/workspace/${workspaceId}/scrum/meeting/create`"
        >회의 추가하기</router-link
      >
    </div>
  </div>
</template>

<style>
.meeting-container {
  padding: 20px;
}
.meeting-card-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 20px;
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
