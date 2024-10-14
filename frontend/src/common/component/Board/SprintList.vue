<script setup>
import { defineProps } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter();
const route = useRoute();
const workspaceId = route.params.workspaceId;

defineProps({
  items: Array,
});

const navigateToDetail = (index) => {
  router.push(`/workspace/${workspaceId}/scrum/sprint/detail/${index}`);
};
</script>

<template>
  <table class="board-table">
    <thead>
      <tr>
        <th>스프린트 이름</th>
        <th>라벨</th>
        <th>상태</th>
        <th>시작일</th>
        <th>마감일</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-for="(item, index) in items"
        :key="index"
        @click="navigateToDetail(item.sprintId)"
      >
        <td class="title">
          {{ item.title }}
        </td>
        <td>
          <span v-if="item.label" class="label">
            {{ item.label }}
          </span>
          <span v-else> None </span>
        </td>
        <td>
          <span class="status">
            {{ item.status }}
          </span>
        </td>
        <td>
          <span>
            {{ item.startDate }}
          </span>
        </td>
        <td>
          <span>
            {{ item.endDate }}
          </span>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<style scoped>
a {
  text-decoration: none;
  color: #28303f;
}

.board-table {
  width: 100%;
  border-collapse: collapse;
  margin: 0 30px;
}

.board-table th,
.board-table td {
  padding: 10px;
  text-align: center;
  border-bottom: 1px solid #e6e9f4;
}

.board-table th {
  background-color: #fff;
  font-size: 14px;
  font-weight: 400;
  color: #5a607f;
}

input[type='checkbox'] {
  display: none;
}
input[type='checkbox'] + label {
  display: inline-block;
  width: 20px;
  height: 20px;
  border: 1px solid #d7dbec;
  position: relative;
}
input[id='check']:checked + label::after {
  content: '✔';
  font-size: 15px;
  width: 20px;
  height: 20px;
  text-align: center;
  position: absolute;
  left: 0;
  top: 0;
}

.title {
  text-align: left;
  padding-left: 20px !important;
}

.label {
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(234, 179, 8, 0.2);
  color: rgba(234, 179, 8);
}

.status {
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(168, 85, 247, 0.2);
  color: rgba(168, 85, 247);
}

.priority {
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(236, 72, 153, 0.2);
  color: rgba(236, 72, 153);
}

.taskNum {
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(34, 197, 94, 0.2);
  color: rgba(34, 197, 94);
}
</style>
