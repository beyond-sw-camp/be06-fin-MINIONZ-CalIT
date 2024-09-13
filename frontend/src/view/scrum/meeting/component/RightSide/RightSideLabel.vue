<script setup>
import { ref } from 'vue';

const taskName = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');

const addTask = () => {
  console.log({
    taskName: taskName.value,
    level: selectedLevel.value,
    priority: selectedPriority.value
  });
  taskName.value = '';
  selectedLevel.value = '';
  selectedPriority.value = '';
};
</script>

<template>
  <div class="form-container">
    <h2>Task 추가하기</h2>

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
    <select
        id="level"
        v-model="selectedLevel"
        class="input-field"
    >
      <option disabled value="">Level</option>
      <option>Easy</option>
      <option>Medium</option>
      <option>Hard</option>
    </select>

    <!-- 중요도 선택 드롭다운 -->
    <label for="priority">중요도</label>
    <select
        id="priority"
        v-model="selectedPriority"
        class="input-field"
    >
      <option disabled value="">Priority</option>
      <option>Low</option>
      <option>Medium</option>
      <option>High</option>
    </select>

    <!-- 추가 버튼 -->
    <button @click="addTask" class="add-task-btn">Add Task</button>
  </div>
</template>

<style scoped>
.form-container {
  width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: bold;
  margin-top: 15px;
}

.input-field {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.add-task-btn {
  background-color: #007bff;
  color: white;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-task-btn:hover {
  background-color: #0056b3;
}
</style>