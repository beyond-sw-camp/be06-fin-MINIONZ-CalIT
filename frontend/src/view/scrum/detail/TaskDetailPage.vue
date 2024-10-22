<script setup>
import { useRoute } from 'vue-router';
import { inject, onMounted, ref } from 'vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';

const route = useRoute();

const taskId = route.params.tasktId;
const workspaceId = route.params.workspaceId;

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = 'Task Detail';
contentsDescription.value = '스프린트 정보를 확인해보세요!';

const taskStore = useTaskStore();
const task = ref(null);

onMounted(async () => {
  await taskStore.getTask(workspaceId, taskId);
  task.value = taskStore.tasks.values;
});
</script>

<template>
  <div class="task-wrap">
    <div class="task-note-container">
      <div class="task-title-container">
        <span class="column">
          <i class="task-title column-icon"></i>
          태스크 제목
        </span>
        <p class="title-editor">{{ task.value.taskTitle }}</p>
      </div>
      <div class="issue-section">
        <span class="column">
          <i class="task-description column-icon"></i>
          설명 추가하기
        </span>
        <p class="description-editor">{{ task.value.taskContents }}</p>
      </div>
      <div class="author-section">
        <div class="participants">
          <span class="column">
            <i class="user-multiple column-icon"></i>
            태스크 참여자
          </span>
          <div class="users-list">
            <div
              class="user-profile"
              v-for="participant in task.value.participants"
              :key="participant.id"
            >
              <img :src="participant.avatar" alt="참여자" />
              <span>{{ participant.name }}</span>
            </div>
          </div>
        </div>
      </div>
      <div class="issue-section">
        <span class="column">
          <i class="label-add column-icon"></i>
          라벨
        </span>
        <div class="label-list">
          <button
            class="label-button"
            v-for="label in task.value.labels"
            :key="label.id"
          >
            {{ label.name }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
  text-align: center;
}
.task-wrap {
  display: flex;
  gap: 1rem;
}

.task-note-container {
  padding: 30px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
}

.task-title-container {
  display: flex;
}

.user-multiple {
  background-image: url('@/assets/icon/boardIcon/userMultiple.svg');
}

.column {
  display: flex;
  align-items: center;
  width: 10rem;
  gap: 10px;
}

.author-section {
  display: flex;
  flex-direction: column;
}

.issue-section {
  display: flex;
}

.author,
.participants {
  display: flex;
  align-items: center;
}

.task-title {
  background-image: url('@/assets/icon/boardIcon/titleEdit.svg');
}

.task-description {
  background-image: url('@/assets/icon/boardIcon/quillDescription.svg');
}

.column-icon {
  background-size: cover;
  width: 24px;
  height: 24px;
  display: block;
}

.user-editor {
  background-image: url('@/assets/icon/boardIcon/userEdit.svg');
}

.author img,
.participants img {
  border-radius: 50%;
  width: 30px;
  height: 30px;
}

.label-add {
  background-image: url('@/assets/icon/boardIcon/labelIcon.svg');
}

.label-button {
  background-color: #fbdbea;
  color: #db2777;
  border: none;
  padding: 5px 10px;
  border-radius: 15px;
  cursor: pointer;
}

.issue-button {
  background-color: #f8d7da;
  color: #c82333;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
}

.issue-id {
  color: #28303f;
  background-color: #f3f6ff;
  padding: 5px 10px;
  border-radius: 15px;
  font-size: 12px;
}

.issue-add {
  background-image: url('@/assets/icon/boardIcon/issueAdd.svg');
}

.task-add {
  background-image: url('@/assets/icon/boardIcon/taskAdd.svg');
}

.title-editor {
  font-size: 1.5rem;
  border: 0;
}

.description-editor {
  font-size: 1rem;
  border: 0;
  font-weight: 400;
  width: 100%;
  margin-left: 15px;
}

.content-editor {
  min-height: 500px;
  padding: 10px;
  background-color: white;
  margin-top: 10px;
  border-top: 1px solid #28303f;
  position: relative;
}

.save-button {
  background-color: #e0e8ff;
  color: #666daf;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
  width: 150px;
  margin-left: auto;
  text-align: center;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
}

.users-list {
  display: flex;
  gap: 10px;
}

.fade-enter-active,
.fade-leave-active {
  transition: transform 0.5s, opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  transform: translateX(100%);
  opacity: 0;
}

.fade-enter-to,
.fade-leave {
  transform: translateX(0);
  opacity: 1;
}
</style>
