<script setup>
import {ref, defineEmits, onMounted} from "vue";
import { useFriendsStore } from "@/stores/user/useFriendsStore";
import Multiselect from "vue-multiselect";
import {useRoute} from "vue-router";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const friendStore = useFriendsStore();
const participants = ref([]);
const filteredFriends = ref([]);
const emit = defineEmits(['update-participants', 'update-qa-participants']);

const searchFriends = async () => {
  try {
    await friendStore.getFriendsList(workspaceId);
    filteredFriends.value = friendStore.friends;
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

const deleteParticipant = (searchUserIdx) => {
  participants.value = participants.value.filter(participant => participant.searchUserIdx !== searchUserIdx);
};

const saveParticipantsToUserList = () => {
  emit('update-participants', participants.value);
  emit('update-qa-participants', participants.value);
};

onMounted(async () => {
  await searchFriends();
});
</script>

<template>
  <div class="form-container">
    <h2>participants 추가하기</h2>
    <hr/>
    <div class="participants-wrap">
      <div>
        <label for="participants">참여자 선택</label>
        <multiselect
            v-model="participants"
            :options="filteredFriends"
            :searchable="true"
            :close-on-select="true"
            :show-labels="false"
            placeholder="참여자를 선택하세요"
            label="userName"
            track-by="searchUserIdx"
        />
<!--        <p class="error-message" v-if="errors.participants">{{ errors.participants }}</p>-->
        <div class="selections participants" v-if="participants && participants.length">
              <span class="item" v-for="participant in participants" :key="participant.searchUserIdx">
                {{ participant.userName }}
                <span @click="deleteParticipant(participant.searchUserIdx)"
                      style="cursor: pointer; margin: 0 10px; padding: 0">x</span>
              </span>
        </div>
      </div>
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