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
    <hr/>
    <div class="issue-wrap">
      <div class="input-wrap">
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
  </div>
    <!-- 추가 버튼 -->
    <button @click="addIssue" class="add-issue-btn">Add Issue</button>
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

label {
  display: block;
  font-weight: 400;
  margin-top: 15px;
  font-size: 16px;
}

hr{
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

.issue-wrap{
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

.add-issue-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-issue-btn:hover {
  background-color: #93AAFD;
}
</style>