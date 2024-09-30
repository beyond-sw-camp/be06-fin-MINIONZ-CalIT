<script setup>
import {computed, defineProps} from 'vue';
import {workspaceData} from "@/static/workspaceData";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));

defineProps({
  items: Array,
  thcolumn: String,
  column: String,
  boardType: String,
});
</script>

<template>
  <table class="board-table">
    <thead>
    <tr>
<!--      <th>-->
<!--        <input type="checkbox" id="check-all"/>-->
<!--        <label for="check-all" class="check-box"></label>-->
<!--      </th>-->
      <th>게시글 제목</th>
      <th>작성자</th>
      <th>{{thcolumn}}</th>
      <th>작성 시간</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(item, index) in items" :key="index">
<!--      <td>-->
<!--        <input type="checkbox" id="check"/>-->
<!--        <label for="check" class="check-box"></label>-->
<!--      </td>-->
      <td class="title">
        <router-link :to="`/workspace/${workspace}/scrum/board/${boardType}/detail/${index}`">
        {{ item.title }}
        </router-link>
      </td>
      <td>{{ item.author }}</td>
      <td>{{ item[column] }}</td>
      <td>{{ item.date }}</td>
      <td class="action-bundle">
        <button @click="$emit('edit-item', item)" class="action-btn edit-btn">
          <i class="edit-icon"></i>
        </button>
        <button @click="$emit('delete-item', item)" class="action-btn delete-btn">
          <i class="delete-icon"></i>
        </button>
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