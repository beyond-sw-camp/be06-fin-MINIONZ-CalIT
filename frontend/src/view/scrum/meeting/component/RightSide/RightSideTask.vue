<script setup>
import { ref } from 'vue';

const taskName = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');

const addTask = () => {
  console.log({
    taskName: taskName.value,
    level: selectedLevel.value,
    priority: selectedPriority.value,
  });
  taskName.value = '';
  selectedLevel.value = '';
  selectedPriority.value = '';
};
</script>

<template>
  <div class="form-container">
    <h2>Task 추가하기</h2>
    <hr />
    <div class="task-wrap">
      <div class="input-wrap">
        <!-- Task 이름 입력 필드 -->
        <label for="task-name">Name Task</label>
        <input
          type="text"
          id="task-name"
          v-model="taskName"
          placeholder="Name Task"
          class="input-field"
        />

        <!-- 난이도 선택 드롭다운 -->
        <label for="level">난이도</label>
        <select id="level" v-model="selectedLevel" class="input-field">
          <option disabled value="">Level</option>
          <option>Easy</option>
          <option>Medium</option>
          <option>Hard</option>
        </select>

        <!-- 중요도 선택 드롭다운 -->
        <label for="priority">중요도</label>
        <select id="priority" v-model="selectedPriority" class="input-field">
          <option disabled value="">Priority</option>
          <option>Low</option>
          <option>Medium</option>
          <option>High</option>
        </select>
      </div>
      <!-- 추가 버튼 -->
      <button @click="addTask" class="add-task-btn">Add Task</button>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  height: calc(100vh - 250px);
  box-sizing: border-box;
  //width: 300px;
  //margin: 0 auto;
  //padding: 20px;
  //border: 1px solid #ddd;
  //border-radius: 8px;
  //background-color: #fff;
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
  background-color: #c6d2fd;
  color: #28303f;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-task-btn:hover {
  background-color: #93aafd;
}
</style>
