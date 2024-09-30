<script setup>
import {ref} from 'vue';
import router from "@/router";
import {useChatRoomStore} from "@/stores/chat/useChatRoomStore";
import { useParticipants } from '@/stores/user/useParticipantsStore';
import { useWorkspaceStore } from '@/stores/workspace/space/useWorkspaceStore';

const chatRoomStore = useChatRoomStore();
console.log('chatRoomStore:', chatRoomStore);
const workspaceId = useWorkspaceStore().workspaceId;
const isModalOpen = ref(true);
const chattingRoomName = ref('');
// const participantInput = ref('');
const { participants, addParticipant, removeParticipant, filteredUsers, participantsName, saveParticipants, userList } = useParticipants();

const closeModal = () => {
  isModalOpen.value = false;
};

const saveParticipantsToUserList = async () => {
  console.log('Before saving participants:', participants.value);
  if (saveParticipants) {
    console.log('Before Saving Chat Room Name:', chattingRoomName.value);// 여기 값안옴
    console.log('After Saving Chat Room Name:', chattingRoomName.value); // 여기 값 안옴
    console.log('Participants:', participants.value);
    const newChatRoomId = chatRoomStore.addChatRoom({ chatroomName: chattingRoomName.value, participants: [...participants.value] }).value;
    console.log('newChatRoomId:', newChatRoomId);
    saveParticipants(userList);
    await router.push(`/workspace/${workspaceId}/chat/${newChatRoomId}`);
    closeModal();
  } else {
    console.warn("saveParticipants is not defined");
  }
  console.warn("saveParticipants is not working");
};

</script>

<template>
  <div class="modal-overlay" v-if="isModalOpen">
    <div class="modal">
      <div class="modal-header">
        <p>Add Chat Room</p>
        <button @click="closeModal" class="close-btn">✕</button>
      </div>
    <div class="modal-container">
      <div class="modal-body">
        <div class="input-wrap">
          <label for="chatroom-name">채팅방 이름 설정</label>
          <input
              type="text"
              id="chatroom-name"
              v-model="chattingRoomName"
              placeholder="채팅방 이름을 입력하세요"
              class="input-field"
          />
        </div>

        <div class="add-participants">
          <div class="add-participants-input">
            <label for="participants-name">참여자 검색</label>
            <input
                type="text"
                id="participants-name"
                v-model="participantsName"
                placeholder="참여자를 추가하세요"
                class="input-field"
            />
          </div>
          <div v-if="participantsName" class="search-results">
            <div v-for="user in filteredUsers"
                 :key="user.id"
                 @click="() => { addParticipant(user); participantsName = ''; }"
            >
              <img :src="user.persona" alt="persona">
              <span class="participant-name">{{ user.username }}</span>
            </div>
          </div>
        </div>
        <div class="participants-list">
          <div v-for="(participant) in participants" :key="participant.id" class="participants-item">
            <div class="participants-item-info">
              <img :src="participant.persona" alt="persona" class="persona">
              <span>{{ participant.username }}</span>
            </div>
            <button @click="removeParticipant(participant.id)" class="del-btn participants-btn">삭제</button>
          </div>
        </div>

      </div>

      <div class="modal-footer">
        <button @click="saveParticipantsToUserList" class="save-btn participants-btn">저장하기</button>
        <button @click="closeModal" class="cancel-btn">취소하기</button>
      </div>
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
  height: 60%;
  box-sizing: border-box;
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

.modal-container{
  height: calc(100% - 50px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
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
  flex-direction: column;
  //margin-bottom: 20px;
  position: relative;
}

.participant-name {
  flex-grow: 1;
}


.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.save-btn {
  background-color: #4a90e2;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
}

label{
  margin-bottom: 10px;
}

.add-participants-input{
  width: 100%;
}

.participants-list{
  max-height: 200px;
  overflow-y: auto;
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
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
  position: absolute;
  top: 60px;
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

.cancel-btn {
  background: none;
  border: 1px solid #666daf;
  cursor: pointer;
  width: 100%;
  border-radius: 5px;
}
</style>