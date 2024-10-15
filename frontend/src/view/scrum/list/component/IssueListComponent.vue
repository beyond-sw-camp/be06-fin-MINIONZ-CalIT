<script setup>
import { defineProps } from 'vue';

defineProps({
  items: Array,
});
</script>

<template>
  <table class="board-table">
    <thead>
    <tr>
      <th>이슈 이름</th>
      <th>상태</th>
      <th>담당자</th>
      <th>이슈 내용</th>
      <th>시작일</th>
      <th>종료일</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, index) in items" :key="index">
      <td class="title">
          {{ item.title }}
      </td>
      <td>
        <span :class="item.status ? 'ongoing' : 'complete'">
          {{ item.status }}
        </span>
      </td>
      <td>
        <span class="title">
          {{ item.manager }}
        </span>
      </td>
      <td>
        <span class="title">
          {{ item.contents }}
        </span>
      </td>
      <td>
        <span class="title">
          {{ item.startDate }}
        </span>
        </td>
      <td>
        <span class="title">
          {{ item.endDate }}
        </span>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<style scoped>
a{
  text-decoration: none;
  color: #28303F;
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
  border-bottom: 1px solid #E6E9F4;
}

.board-table th {
  background-color: #fff;
  font-size: 14px;
  font-weight: 400;
  color: #5A607F;
}

.title {
  text-align: left;
  padding-left: 20px !important;
}

.ongoing{
  background-color: rgba(236, 72, 153, 0.1);
  color: rgba(236, 72, 153);
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
}

.complete{
  background-color: rgba(34, 197, 94, 0.1);
  color: rgba(34, 197, 94);
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
}
</style>