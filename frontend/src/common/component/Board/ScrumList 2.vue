<script setup>
import {computed, defineProps} from 'vue';
import {workspaceData} from "@/static/workspaceData";
import {useRoute} from "vue-router";


const route = useRoute();
const workspaceId = route.params.workspaceId;
const workspace = computed(() => workspaceData.find(ws => ws.workspaceId === workspaceId));

defineProps({
  items: Array,
  firstColumn: String,
  secondColumn: String,
  thirdColumn: String,
  fourthColumn: String,
  fifthColumn: String,
  endDate: String,
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
      <th>{{firstColumn}}</th>
      <!--      <th>작성자</th>-->
      <th>{{secondColumn}}</th>
      <th>{{thirdColumn}}</th>
      <th>{{fourthColumn}}</th>
      <th>{{fifthColumn}}</th>
      <th>{{endDate}}</th>
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
        <router-link :to="`/workspace/${workspace}/scrum/${boardType}/detail/${index}`">
          {{ item.title }}
        </router-link>
      </td>
      <td >
        <span class="label">
          {{ item.label }}
        </span>
      </td>
      <td>
        <span class="status">
          {{ item.status }}
        </span>
        </td>
      <td>
        <span class="priority">
          {{ item.priority }}
        </span>
        </td>
      <td>
        <span class="taskNum">
          {{ item.taskNumber}}
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

.label{
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(234, 179, 8, 0.2);
  color: rgba(234, 179, 8)
}

.status{
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(168, 85, 247, 0.2);
  color: rgba(168, 85, 247)
}

.priority{
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(236, 72, 153, 0.2);
  color: rgba(236, 72, 153)
}

.taskNum{
  padding: 5px 8px;
  border-radius: 15px;
  font-size: 14px;
  background-color: rgba(34, 197, 94, 0.2);
  color: rgba(34, 197, 94)
}
</style>