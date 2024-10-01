<script setup>
import { computed, ref, watch } from 'vue';
import { useTaskStore } from '@/stores/workspace/scrum/useTaskStore';
import Multiselect from 'vue-multiselect';
import { useFriendsStore} from "@/stores/friends/useFriendsStore";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const taskStore = useTaskStore();
const taskName = ref('');
const taskContent = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');
const userSearch = ref('');
const selectedTasks = ref([]);
const assignees = ref([]);
const reviewers = ref([]);
const startTime = ref('');
const endTime = ref('');
const friendStore = useFriendsStore();
const filteredUsers = computed(() => {
  return friendStore.getFilteredUsers(workspaceId, userSearch.value);
});

const filteredTasks = computed(() => {
  const tasks = taskStore.getTaskTitle();
  if (!tasks) return [];
  return tasks.filter(task => task.title.toLowerCase().includes(String(userSearch.value).toLowerCase()));
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

watch(userSearch, (newTask) => {
  if (newTask && !selectedTasks.value.includes(newTask)) {
    selectedTasks.value.push(newTask);
  }
});
</script>

<template>
  <div class="form-container">
    <h2>Task 추가하기</h2>
    <hr/>

    <div class="task-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="task-select">기존 Task 선택</label>
            <multiselect
                v-model="userSearch"
                :options="filteredTasks"
                :searchable="true"
                :close-on-select="true"
                :show-labels="false"
                placeholder="기존 Task 연동하기"
                label="title"
                track-by="id"
            />
          </div>
          <div v-if="selectedTasks && selectedTasks.value && selectedTasks.value.length > 0">
            <p>연동할 Tasks</p>
            <ul>
              <li v-for="task in selectedTasks" :key="task.id">{{ task.title }}</li>
            </ul>
          </div>
        </div>
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
        <label for="level">난이도</label>
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
  height: calc(100vh - 250px);
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