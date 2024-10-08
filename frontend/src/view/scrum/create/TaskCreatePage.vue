<script setup>
import { inject, ref} from 'vue';
import { useTaskStore } from "@/stores/scrum/useTaskStore";
import { useTaskLabelStore } from "@/stores/scrum/useTaskLabelStore";
import Multiselect from "vue-multiselect";
import {timeInputUtils} from "@/utils/timeInputUtils";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = '스프린트 추가하기';
contentsDescription.value = '스프린트를 추가해보세요!';

const taskStore = useTaskStore();
const taskLabelStore = useTaskLabelStore();
const taskName = ref('');
const participants = ref('');
const selectedLabels = ref([]);
const startTime = ref('');
const endTime = ref('');

const addTask = () => {
  taskStore.addTask({
    taskName: taskName.value,
    participants: participants.value,
  });
  taskName.value = '';
  participants.value = '';
};

const adjustTime = () => {
  if (startTime.value && endTime.value) {
    const { start, end } = timeInputUtils(startTime.value, endTime.value);
    startTime.value = start;
    endTime.value = end;
  }
};
</script>

<template>
  <div class="form-container">
    <div class="workspace-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="taskName">Task 이름</label>
            <input type="text" id="taskName" v-model="taskName" placeholder="Task 이름을 입력하세요">
          </div>
          <div>
            <label for="taskContent">Task 내용</label>
            <input type="text" id="taskContent" placeholder="Task 내용을 입력하세요" class="input-field">
          </div>
          <!--          <div>-->
          <!--            <label>스프린트 매니저</label>-->
          <!--            <multiselect-->
          <!--                v-model="TaskManager"-->
          <!--                :options="filteredUsers"-->
          <!--                placeholder="담당자를 선택해주세요"-->
          <!--                label="name"-->
          <!--                track-by="id"-->
          <!--            ></multiselect>-->
          <!--          </div>-->
          <div>
            <label for="taskParticipation">참여자 추가</label>
            <input type="text" id="taskParticipation" placeholder="참여자를 검색해주세요" class="input-field">
            <ul>
              <li v-for="participant in participants" :key="participant">{{ participant }}</li>
            </ul>
          </div>
          <div>
            <label>시작 날짜</label>
            <input v-model="startTime" type="date" id="startDate" class="input-field" @change="adjustTime">
          </div>
          <div>
            <label>종료 날짜</label>
            <input v-model="endTime" type="date" id="endDate" class="input-field" @change="adjustTime">
          </div>
          <div>
            <label>label 추가하기</label>
            <multiselect
                v-model="selectedLabels"
                :options="taskLabelStore.getTaskLabel()"
                placeholder="label을 추가해주세요"
                label="labelName"
                track-by="labelId"
                multiple="multiple"
            ></multiselect>
          </div>
        </div>
      </div>
      <div class="button-wrap">
        <button @click="addTask" class="add-workspace-btn">Task 추가</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

h2 {
  font-size: 24px;
  font-weight: 500;
  margin: 0;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

label {
  display: block;
  font-weight: 400;
  margin-top: 15px;
  font-size: 16px;
  margin-bottom: 5px;
}

.workspace-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.input-field {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.add-workspace-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 20px;
}

.add-workspace-btn:hover {
  background-color: #93AAFD;
}
</style>