<script setup>
import { useParticipants } from "@/stores/user/useParticipantsStore";

const {
  participantsName,
  participants,
  userList,
  filteredUsers,
  addParticipant,
  removeParticipant,
  saveParticipants
} = useParticipants();

const saveParticipantsToUserList = () => {
  if (saveParticipants) {
    saveParticipants(userList);
  } else {
    console.warn("saveParticipants is not defined");
  }
};
</script>

<template>
  <div class="form-container">
    <h2>participants 추가하기</h2>
    <hr/>
    <div class="participants-wrap">
      <div>
        <div class="input-wrap">
          <label for="participants-name">참여자 검색</label>
          <div class="input-btn-wrap">
            <input
                type="text"
                id="participants-name"
                v-model="participantsName"
                placeholder="참여자 이름을 입력하세요"
                class="input-field"
            />
          </div>
          <div v-if="participantsName" class="search-results">
            <div v-for="user in filteredUsers" :key="user.id" @click="() => { addParticipant(user); participantsName = ''; }">
              <img :src="user.persona" alt="persona">
              <span>{{ user.username }}</span>
            </div>
          </div>
        </div>
        <div class="participants-list">
          <div v-for="participant in participants" :key="participant.id" class="participants-item">
            <div class="participants-item-info">
              <img :src="participant.persona" alt="persona" class="persona">
              <span>{{ participant.username }}</span>
            </div>
            <button @click="removeParticipant(participant.id)" class="del-btn participants-btn">삭제</button>
          </div>
        </div>
      </div>

      <!-- 저장 버튼 -->
      <button @click="saveParticipantsToUserList" class="save-btn participants-btn">저장하기</button>
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