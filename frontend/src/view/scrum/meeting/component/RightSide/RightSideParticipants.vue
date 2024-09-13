<script setup>
import { ref } from 'vue';

const participantsName = ref('');
const selectedLevel = ref('');
const selectedPriority = ref('');

const addParticipants = () => {
  console.log({
    participantsName: participantsName.value,
    level: selectedLevel.value,
    priority: selectedPriority.value
  });
  participantsName.value = '';
  selectedLevel.value = '';
  selectedPriority.value = '';
};
</script>

<template>
  <div class="form-container">
    <h2>participants 추가하기</h2>
    <hr/>
    <div class="participants-wrap">
      <div class="input-wrap">
        <!-- participants 이름 입력 필드 -->
        <label for="participants-name">Name participants</label>
        <input
            type="text"
            id="participants-name"
            v-model="participantsName"
            placeholder="Name participants"
            class="input-field"
        />

        <!-- 난이도 선택 드롭다운 -->
        <label for="level">참여자 검색</label>
        <input
            type="text"
            id="level"
            v-model="selectedLevel"
            placeholder="Level"
            class="input-field"
        />

      </div>
      <!-- 추가 버튼 -->
      <button @click="addParticipants" class="add-participants-btn">Add participants</button>
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

.participants-wrap{
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

.add-participants-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-participants-btn:hover {
  background-color: #93AAFD;
}
</style>