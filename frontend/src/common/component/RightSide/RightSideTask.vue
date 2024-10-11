<script setup>
import { computed, ref, watch } from 'vue';
import { useSprintStore } from "@/stores/scrum/useSprintStore";
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import { useFriendsStore } from "@/stores/user/useFriendsStore";
import { useRoute } from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;
const sprintId = ref('');

const taskStore = useTaskStore();
const friendStore = useFriendsStore();
const sprintStore = useSprintStore();

const taskName = ref('');
const taskContent = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');
const userSearch = ref('');
// const selectedTasks = ref([]);
const assignees = ref([]);
const reviewers = ref([]);
const startTime = ref('');
const endTime = ref('');

const filteredFriends = ref([]);
// const selectedFriend = ref(null);

const sprintList = computed(() => {
  return sprintStore.getSprintList(workspaceId);
});

const searchFriends = async () => {
  try {
    await friendStore.getFriendsList(workspaceId);
    filteredFriends.value = friendStore.friends.filter(friend =>
        friend.userName.toLowerCase().includes(userSearch.value.toLowerCase())
    );
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

const toggleFriendSelection = (friend) => {
  if (!assignees.value.includes(friend)) {
    assignees.value.push(friend);
  }
};

const removeFriendSelection = (friend) => {
  assignees.value = assignees.value.filter(f => f !== friend);
};

// const filteredTasks = computed(() => {
//   if (!sprintId.value) return [];
//   const tasks = taskStore.getTaskList(sprintId.value);
//   return tasks.filter(task => !selectedTasks.value.includes(task));
// });

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

watch(userSearch, (newSearch) => {
  if (newSearch) {
    searchFriends();
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
          <label for="sprint-select">Sprint 선택</label>
          <select v-model="sprintId" @change="taskStore.getTaskList(sprintId.value)" class="input-field">
            <option v-for="sprint in sprintList" :key="sprint.id" :value="sprint.id">
              {{ sprint.title }}
            </option>
          </select>
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
          <label for="taskAssignee">담당자</label>
          <input type="text" v-model="userSearch" placeholder="담당자 검색" class="input-field"/>
          <div class="search-results" v-if="filteredFriends.length">
            <div v-for="friend in filteredFriends" :key="friend.id" @click="toggleFriendSelection(friend)">
              <img :src="friend.avatar" alt="avatar" class="persona"/>
              {{ friend.userName }}
            </div>
          </div>
          <ul class="participants-list">
            <li v-for="assignee in assignees" :key="assignee.id" class="participants-item">
              <div class="participants-item-info">
                <img :src="assignee.avatar" alt="avatar" class="persona"/>
                {{ assignee.userName }}
              </div>
              <button @click="removeFriendSelection(assignee)" class="del-btn">삭제</button>
            </li>
          </ul>
        </div>
        <div>
          <label for="taskReviewer">보고자</label>
          <select id="taskReviewer" v-model="reviewers" class="input-field">
            <option v-for="user in filteredFriends" :key="user.id" :value="user">{{ user.userName }}</option>
          </select>
        </div>
        <div class="time-wrap">
          <label>시작 날짜</label>
          <input v-model="startTime" type="datetime-local" class="time-editor"/>
          <span>~ 종료 날짜</span>
          <input v-model="endTime" type="datetime-local" class="time-editor"/>
        </div>
        <label for="level">난이도</label>
        <div>
          <label for="level">난이도</label>
          <select v-model="selectedLevel" class="input-field">
            <option value="Easy">Easy</option>
            <option value="Medium">Medium</option>
            <option value="Hard">Hard</option>
          </select>
        </div>
        <div>
          <label for="priority">중요도</label>
          <select v-model="selectedPriority" class="input-field">
            <option value="Low">Low</option>
            <option value="Medium">Medium</option>
            <option value="High">High</option>
          </select>
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

.time-wrap {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-direction: column;
}

.time-editor {
  border: none;
  width: 200px;
}
</style>