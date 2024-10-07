<script setup>
import {computed, inject, ref, watch} from 'vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import Multiselect from 'vue-multiselect';
import { useFriendsStore} from "@/stores/user/useFriendsStore";
import {useRoute} from "vue-router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Work Space Task 추가하기';
contentsDescription.value = 'Work Space Task를 추가해보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const taskStore = useTaskStore();
const taskName = ref('');
const taskContent = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');
const taskSearch = ref('');
const selectedTasks = ref([]);
const assignees = ref('');
const reviewers = ref('');
const startTime = ref('');
const endTime = ref('');
const friendStore = useFriendsStore();
const filteredUsers = computed(() => {
  return friendStore.getFilteredUsers(workspaceId, taskSearch.value);
});

const addTask = () => {
  taskStore.addTask({
    taskName: taskName.value,
    taskContent: taskContent.value,
    assignees: assignees.value,
    reviewers: reviewers.value,
    level: selectedLevel.value,
    priority: selectedPriority.value
  });
  taskName.value = '';
  selectedLevel.value = '';
  selectedPriority.value = '';
};

watch(taskSearch, (newTask) => {
  if (newTask && !selectedTasks.value.includes(newTask)) {
    selectedTasks.value.push(newTask);
  }
});
</script>

<template>
  <div class="form-container">
    <div class="task-wrap">
      <div class="input-wrap">
        <div>
          <label for="task-name">Task 제목</label>
          <input type="text" id="task-name" v-model="taskName" placeholder="Task 제목을 적어주세요" class="input-field"/>
        </div>
        <div>
          <label for="task-content">Task 내용</label>
          <textarea id="task-content" v-model="taskContent" placeholder="Task 내용을 적어주세요" class="input-field" style="margin:0"/>
        </div>
        <div>
          <label>담당자</label>
          <multiselect
              v-model="assignees"
              :options="filteredUsers"
              :searchable="true"
              :close-on-select="true"
              :show-labels="false"
              placeholder="담당자를 선택해주세요"
              label="name"
              track-by="id"
          />
        </div>
        <div>
          <label>보고자</label>
          <multiselect
              v-model="reviewers"
              :options="filteredUsers"
              :searchable="true"
              :close-on-select="true"
              :show-labels="false"
              placeholder="보고자를 선택해주세요"
              label="name"
              track-by="id"
          />
        </div>
        <div class="time-wrap">
          <label>시작 날짜</label>
          <input v-model="startTime" type="datetime-local" class="time-editor" />
          <span>~ 종료 날짜</span>
          <input v-model="endTime" type="datetime-local" class="time-editor" />
        </div>
        <div>
          <label for="level">난이도</label>
          <multiselect
              v-model="selectedLevel"
              :options="['Easy', 'Medium', 'Hard']"
              placeholder="Level"
          />
        </div>
        <div>
          <label for="priority">중요도</label>
          <multiselect
              v-model="selectedPriority"
              :options="['Low', 'Medium', 'High']"
              placeholder="Priority"
          />
        </div>
      </div>
      <button @click="addTask" class="add-task-btn">Task 연동하기</button>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  box-sizing: border-box;
  padding: 30px;
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

.task-wrap {
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

.add-task-btn {
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

.add-task-btn:hover {
  background-color: #93AAFD;
}

.multiselect__select {
  top: 10px;
}

.multiselect .input-field {
  padding: 0;
  margin-top: 0;
  margin-bottom: 0;
}

.time-wrap{
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-direction: column;
}

.time-editor{
  border: none;
  width: 200px;
}
</style>