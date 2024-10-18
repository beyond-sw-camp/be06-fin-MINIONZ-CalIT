<script setup>
import { defineProps } from 'vue';
import { useRoute } from "vue-router";
import { formatDate } from "@/utils/timeUtils";
import router from "@/router";

const route = useRoute();
const workspaceId = route.params.workspaceId;

defineProps({
  items: Array,
  thcolumn: String,
  column: String,
  boardType: String,
});

function navigateToDetailPage(item) {
    router.push(`/workspace/${workspaceId}/scrum/board/qa/detail/${item.qaBoardId}`);
}
</script>

<template>
  <table class="board-table">
    <thead>
    <tr>
      <th>게시글 제목</th>
      <th>내용</th>
      <th>Task</th>
      <th>담당자</th>
      <th>작성자</th>
      <th>작성 시간</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, index) in items" :key="index" @click="navigateToDetailPage(item)">
        <td class="title">{{ item.qaboardTitle  }}</td>
          <td>{{ item.qaboardContent }}</td>
        <td>{{ item.taskName  }}</td>
        <td>{{ item.assignUser }}</td>
        <td>{{ item.userName }}</td>
        <td>{{ formatDate(item.createdAt) }}</td>
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
  width: 90%;
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

input[type="checkbox"]{
  display: none;
}
input[type="checkbox"] + label{
  display: inline-block;
  width: 20px;
  height: 20px;
  border:1px solid #D7DBEC;
  position: relative;
}
input[id="check"]:checked + label::after{
  content:'✔';
  font-size: 15px;
  width: 20px;
  height: 20px;
  text-align: center;
  position: absolute;
  left: 0;
  top:0;
}

.title {
  text-align: left;
  padding-left: 20px !important;
}

.action-bundle {
  width: 200px;
}

.action-btn {
  border: none;
  background-color: transparent;
  cursor: pointer;
  margin-right: 5px;
}

.edit-icon {
  width: 40px;
  height: 40px;
  display: block;
  background-image: url("@/assets/icon/boardIcon/boardEdit.svg");
}
.delete-icon {
  width: 40px;
  height: 40px;
  display: block;
  background-image: url("@/assets/icon/boardIcon/boardDelete.svg");
}
</style>