<script setup>
import { ref, onMounted } from "vue";
import { useFriendsStore } from "@/stores/user/useFriendsStore";
import { useRoute } from "vue-router";
import { setPersona } from "@/utils/personaUtils";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const participants = ref([]);
const friendsStore = useFriendsStore();
const friendsList = ref([]);
const selectedFriend = ref(null);

const fetchFriendsList = async () => {
  try {
    friendsList.value = await friendsStore.getFriendsList(workspaceId);
  } catch (error) {
    console.error('Error fetching Friends:', error);
    friendsList.value = [];
  }
};

const toggleFriendSelection = () => {
  if (selectedFriend.value && !participants.value.includes(selectedFriend.value)) {
    participants.value.push(selectedFriend.value);
  }
};

const removeFriendSelection = (friend) => {
  participants.value = participants.value.filter(f => f !== friend);
};

const addParticipant = () => {
  try {
    toggleFriendSelection();
  } catch (error) {
    console.error('Error adding participant:', error);
  }
};

onMounted(() => {
  fetchFriendsList();
});
</script>

<template>
  <div class="form-container">
    <h2>participants 추가하기</h2>
    <hr/>
    <div class="participants-wrap">
      <select v-model="selectedFriend" class="input-field">
        <option disabled value="">참여자 검색</option>
        <option v-for="friend in friendsList" :key="friend.id" :value="friend">
          {{ friend.name }}
        </option>
      </select>
      <ul v-if="selectedFriends && selectedFriends.length" class="participants-list">
        <li v-for="friend in selectedFriends" :key="friend.searchFriendsIdx" class="participants-item">
          <div class="participants-item-info">
            <img :src="setPersona(1)" alt="persona" class="persona">
            <span>{{ friend.userName }}</span>
          </div>
          <button @click="removeFriendSelection(friend)" class="del-btn participants-btn">삭제</button>
        </li>
      </ul>
      <button @click="addParticipant" class="save-btn participants-btn">추가하기</button>
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

hr {
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

.participants-wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

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

.del-btn {
  width: 60px;
}

.input-btn-wrap {
  display: flex;
  gap: 10px;
}

.participants-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  justify-content: space-between;

  img {
    width: 36px;
  }
}

.participants-item-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-results {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
  background-color: #fff;

  div {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    cursor: pointer;

    img {
      width: 36px;
    }

    &:hover {
      background-color: #e0e8ff;
      border: 1px solid #C6D2FD;
    }
  }
}

.participants-list {
  max-height: 150px;
  overflow-y: auto;
  width: 100%;
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

.del-btn {
  width: 60px;
}

.participants-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  justify-content: space-between;

  img {
    width: 36px;
  }
}

.participants-item-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.persona {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  object-fit: cover;
}
</style>