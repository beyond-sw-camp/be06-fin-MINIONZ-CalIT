<script setup>
import { ref, defineProps, defineEmits } from 'vue';
import { useFriendsStore } from "@/stores/user/useFriendsStore";

const { participantsName, participants, filteredUsers, removeParticipant } = useFriendsStore();
const props = defineProps({availableParticipants: Array});
const emit = defineEmits(['participantSelected']);

const selectedParticipants = ref(props.availableParticipants);

const selectParticipant = (participant) => {
  if (!selectedParticipants.value.includes(participant)) {
    selectedParticipants.value.push(participant);
    emit('update:selectedParticipants', selectedParticipants.value);
  }
};
</script>

<template>
  <div class="search-friends">
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
        <div v-for="user in filteredUsers" :key="user.id" @click="() => selectParticipant(user)">
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
</template>

<style scoped>
.input-field {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
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