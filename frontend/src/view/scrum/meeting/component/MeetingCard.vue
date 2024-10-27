<script setup>
import { defineProps } from 'vue';
import { useRoute } from 'vue-router';
import { formatDate } from '@/utils/timeUtils';
import { setPersona } from '@/utils/personaUtils';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const props = defineProps({
  id: Number,
  title: String,
  labels: Array,
  contents: String,
  participants: Array,
  startDate: String,
});
</script>

<template>
  <router-link
    :to="`/workspace/${workspaceId}/scrum/meeting/detail/${props.id}`"
    class="card"
  >
    <div class="card-header">
      <p class="card-title">{{ title }}</p>
      <div>
        <span v-for="label in labels" :key="label.id" class="label-button">{{
          label.labelName
        }}</span>
      </div>
    </div>
    <p class="card-contents">{{ contents }}</p>
    <div class="card-footer">
      <div class="participants">
        <img
          v-for="(image, index) in participants"
          :key="index"
          :src="setPersona(image)"
          alt="Participant"
        />
        <span>{{ participants.length }} 명 참여</span>
      </div>
      <div>
        <span class="applicants">{{ formatDate(startDate) }}</span>
      </div>
    </div>
  </router-link>
</template>

<style scoped>
a {
  text-decoration: none;
}
.card {
  background-color: #f7f8fa;
  border-radius: 8px;
  padding: 20px;
}

.card-contents {
  font-size: 14px;
  color: #657081;
  display: -webkit-box;
  overflow: hidden;
  word-break: break-all;
  word-wrap: break-word;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  height: 45px;
  margin-top: 10px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #28303f;
}

.card-title {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.label-Front {
  color: #9333ea;
  background-color: #f6eefe;
  padding: 3px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.label-Backend {
  color: #ca8a04;
  background-color: #f9f0d0;
  padding: 3px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.label-Design {
  color: #db2777;
  background-color: #fbdbea;
  padding: 3px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.label-DB {
  color: #16a34a;
  background-color: #e9f9ef;
  padding: 3px 10px;
  border-radius: 15px;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.participants {
  display: flex;
  align-items: center;
}

.participants img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 5px;
}

.label-button {
  background-color: #fbdbea;
  color: #db2777;
  border: none;
  padding: 5px 10px;
  border-radius: 15px;
  cursor: pointer;
  margin-right: 5px;
}

.participants span {
  margin-left: 10px;
  color: #28303f;
}

.applicants {
  font-size: 14px;
  font-weight: 400;
  color: #657081;
}
</style>
