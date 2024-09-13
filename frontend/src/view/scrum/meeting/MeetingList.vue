<script setup>
import {computed, inject} from 'vue';
import { useRoute } from 'vue-router';
import { workspaceData } from "@/static/workspaceData";
import MeetingCard from "@/view/scrum/meeting/component/MeetingCard.vue";
import SearchComponent from "@/common/component/SearchComponent.vue";
import {meetingData} from "@/static/meetingData";

const user2 = require('@/assets/icon/persona/user2.svg');
const user3 = require('@/assets/icon/persona/user3.svg');
const user4 = require('@/assets/icon/persona/user4.svg');
const user5 = require('@/assets/icon/persona/user5.svg');

const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));
const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = workspace.value ? `${workspace.value.workspaceName} Meeting` : 'Meeting';
contentsDescription.value = '워크스페이스의 회의 내역을 살펴보세요!';
</script>

<template>
  <div class="meeting-container">
    <SearchComponent :link="`/workspace/${workspaceId}/scrum/meeting/create`"/>
    <div class="meeting-card-container">
<!--  TODO 유저랑 연동시킬 것-->
      <MeetingCard
          v-for="meeting in meetingData"
          :key="meeting.id"
          :id="meeting.id"
          :title="meeting.title"
          label="Front"
          :contents="meeting.contents"
          :participants="meeting.participants.map(p => {
            if (p.userName === '홍길동') return user2;
            if (p.userName === '백설이') return user3;
            if (p.userName === '강백호') return user4;
            return user5;
          })"
          :startDate="meeting.startDate"
      />
      <!-- 추가 카드들... -->
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
</style>