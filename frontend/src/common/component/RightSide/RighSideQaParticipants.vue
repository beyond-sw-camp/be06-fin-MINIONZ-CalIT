<script setup>
import {ref, defineEmits} from "vue";
import SearchQaFriends from "../search/SearchQaFriends.vue";

const participants = ref([]);
const emit = defineEmits(['update-participants']);
const updateSelectedParticipants = (newParticipants) => {
  participants.value = newParticipants;
};

const saveParticipantsToUserList = () => {
  emit('update-participants', participants.value);
};
</script>

<template>
  <div class="form-container">
    <h2>participants 추가하기</h2>
    <hr/>
    <div class="participants-wrap">
      <SearchQaFriends @update-selected-participants="updateSelectedParticipants" />
      <button @click="saveParticipantsToUserList" class="save-btn participants-btn">저장하기</button>
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
  //margin-top: 5px;
  //margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.participants-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.participants-btn:hover {
  background-color: #93AAFD;
}

.del-btn{
  width: 60px;
}

.input-btn-wrap{
  display: flex;
  gap: 10px;
}

.participants-item{
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
  border:  1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  justify-content: space-between;
  img{
    width: 36px;
  }
}

.participants-item-info{
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-results{
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
  //position: absolute;
  //top: 60px;
  background-color: #fff;
  div{
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;
    img{
      width: 36px;
    }
    &:hover{
      background-color: #e0e8ff;
      border: 1px solid #C6D2FD;
    }
  }
}
</style>