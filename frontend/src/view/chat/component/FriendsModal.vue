<script setup>
import user2 from '@/assets/icon/persona/user2.svg';
import user3 from '@/assets/icon/persona/user3.svg';
import user4 from '@/assets/icon/persona/user4.svg';
import user5 from '@/assets/icon/persona/user5.svg';

import {ref} from 'vue';

const isModalOpen = ref(true);
const chattingRoomName = ref('');
const participantInput = ref('');
const participants = ref([
  {name: '박성준', image: user2},
  {name: '최승은', image: user3},
  {name: '지연희', image: user4},
]);

const addParticipant = () => {
  if (participantInput.value.trim() !== '') {
    participants.value.push({name: participantInput.value, image: user5});
    participantInput.value = '';
  }
};

const removeParticipant = (index) => {
  participants.value.splice(index, 1);
};

const closeModal = () => {
  isModalOpen.value = false;
};

const saveRoom = () => {
  console.log('Room saved:', chattingRoomName.value, participants.value);
  closeModal();
};
</script>

<template>
  <div class="modal-overlay" v-if="isModalOpen">
    <div class="modal">
      <div class="modal-header">
        <p>Add Chat Room</p>
        <button @click="closeModal" class="close-btn">✕</button>
      </div>

      <div class="modal-body">
        <input
            type="text"
            v-model="chattingRoomName"
            placeholder="채팅방 명을 입력하세요"
            class="input-field"
        />

        <div class="add-participants">
          <span class="icon">👤</span>
          <input
              type="text"
              v-model="participantInput"
              placeholder="참여자를 추가하세요"
              @keyup.enter="addParticipant"
              class="input-field"
          />
        </div>

        <hr>

        <div v-for="(participant, index) in participants" :key="index" class="participant-item">
          <img :src="participant.image" alt="Participant" class="participant-img"/>
          <span class="participant-name">{{ participant.name }}</span>
          <button @click="removeParticipant(index)" class="remove-btn">✕</button>
        </div>
      </div>

      <div class="modal-footer">
        <button @click="closeModal" class="cancel-btn">Cancel</button>
        <button @click="saveRoom" class="save-btn">Save</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}

.modal {
  background-color: white;
  border-radius: 8px;
  width: 400px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.modal-header p {
  font-size: 18px;
  font-weight: 500;
}

.close-btn {
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
}

.modal-body {
  margin-bottom: 20px;
}

.input-field {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  box-sizing: border-box;
}

.add-participants {
  display: flex;
  align-items: center;
  //margin-bottom: 20px;
  position: relative;

  input {
    padding-left: 35px;
  }
}

.icon {
  margin-left: 10px;
  font-size: 20px;
  position: absolute;
  top: 4px;
}

.participant-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  margin-bottom: 10px;
}

.participant-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.participant-name {
  flex-grow: 1;
}

.remove-btn {
  background-color: transparent;
  border: none;
  font-size: 20px;
  color: red;
  cursor: pointer;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
}

.cancel-btn {
  background: none;
  border: none;
  color: #888;
  margin-right: 20px;
  cursor: pointer;
}

.save-btn {
  background-color: #4a90e2;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

hr {
  margin-bottom: 10px;
  background-color: #f1f1f1;
  width: 100%;
  height: 1px;
}
</style>