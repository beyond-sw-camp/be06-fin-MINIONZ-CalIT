<script setup>
import { ref } from 'vue';

const issueName = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');

const addIssue = () => {
  console.log({
    issueName: issueName.value,
    level: selectedLevel.value,
    priority: selectedPriority.value
  });
  issueName.value = '';
  selectedLevel.value = '';
  selectedPriority.value = '';
};
</script>

<template>
  <div class="form-container">
    <h2>Issue 추가하기</h2>

    <!-- Issue 이름 입력 필드 -->
    <label for="issue-name">Name Issue</label>
    <input
        type="text"
        id="issue-name"
        v-model="issueName"
        placeholder="Name Issue"
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
    <button @click="addIssue" class="add-issue-btn">Add Issue</button>
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

.add-issue-btn {
  background-color: #007bff;
  color: white;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-issue-btn:hover {
  background-color: #0056b3;
}
</style>