<script setup>
import { ref } from 'vue';

const labelName = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');

const addLabel = () => {
  console.log({
    labelName: labelName.value,
    level: selectedLevel.value,
    priority: selectedPriority.value
  });
  labelName.value = '';
  selectedLevel.value = '';
  selectedPriority.value = '';
};
</script>

<template>
  <div class="form-container">
    <h2>label 추가하기</h2>
    <hr/>
    <div class="label-wrap">
      <div class="input-wrap">
    <!-- label 이름 입력 필드 -->
    <label for="label-name">Name label</label>
    <input
        type="text"
        id="label-name"
        v-model="labelName"
        placeholder="Name label"
        class="input-field"
    />
  </div>
    <!-- 추가 버튼 -->
    <button @click="addLabel" class="add-label-btn">Add label</button>
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

hr{
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

.label-wrap{
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

.add-label-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-label-btn:hover {
  background-color: #93AAFD;
}
</style>