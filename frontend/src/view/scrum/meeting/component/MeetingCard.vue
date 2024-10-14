<script setup>
import { defineProps, computed } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const props = defineProps({
  id: Number,
  title: String,
  label: String,
  contents: String,
  participants: Array,
  startDate: String
});

const labelClass = computed(() => {
  if (props.label === 'Front') return 'label-Front';
  if (props.label === 'Backend') return 'label-Backend';
  if (props.label === 'Design') return 'label-design';
  if (props.label === 'DB') return 'label-DB';
  return 'label-default';
});
</script>

<template>
  <router-link :to="`/workspace/${workspaceId}/scrum/meeting/detail/${props.id}`" class="card">
    <div class="card-header">
      <p class="card-title">{{ title }}</p>
      <span :class="labelClass">{{ label }}</span>
    </div>
    <p class="card-contents">{{ contents }}</p>
    <div class="card-footer">
      <div class="participants">
        <img v-for="(image, index) in participants" :key="index" :src="image" alt="Participant" />
        <span>{{ participants.length }} 명 참여</span>
      </div>
      <div>
        <span class="applicants">{{ startDate }}</span>
      </div>
    </div>
  </router-link>
</template>

<style scoped>
a{
  text-decoration: none;
}
.card {
  background-color: #F7F8FA;
  border-radius: 8px;
  padding: 20px;
  //box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
}

.card-contents {
  //margin-top: 10px;
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
  color: #28303F;
}

.card-title {
  margin: 0;
  font-size: 16px;
  font-weight: 500;
}

.label-Front {
  color: #9333EA;
  background-color: #F6EEFE;
  padding: 3px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.label-Backend {
  color: #CA8A04;
  background-color: #F9F0D0;
  padding: 3px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.label-Design {
  color: #DB2777;
  background-color: #FBDBEA;
  padding: 3px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.label-DB {
  color: #16A34A;
  background-color: #E9F9EF;
  padding: 3px 10px;
  border-radius: 15px;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

.participants{
  display: flex;
  align-items: center;
}

.participants img {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 5px;
}

.participants span {
  margin-left: 10px;
  color: #28303F;
}

.applicants {
  font-size: 14px;
  font-weight: 400;
  color: #657081;
}
</style>