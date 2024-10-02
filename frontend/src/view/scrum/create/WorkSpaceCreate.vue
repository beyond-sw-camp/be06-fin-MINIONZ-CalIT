<script setup>
import {inject, onMounted, ref, watch} from 'vue';
import {useWorkspaceStore} from '@/stores/workspace/space/useWorkspaceStore';
import {useFriendsStore} from '@/stores/friends/useFriendsStore';
import router from "@/router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'WorkSpace 추가하기';
contentsDescription.value = 'WorkSpace를 추가해보세요!';

const workspaceStore = useWorkspaceStore();
const friendStore = useFriendsStore();
const workspaceName = ref('');
const participants = ref([]);
const filteredUsers = ref([]);
const selectedUsers = ref([]);

onMounted(async () => {
  await searchUsers();
});

watch(participants, async () => {
  await searchUsers();
});

const searchUsers = async () => {
  console.log('Searching users with participants:', participants.value);
  if (participants.value) {
    try {
      await friendStore.getUserList(participants.value);
      console.log('Fetched friends from store:', friendStore.friends);
      filteredUsers.value = friendStore.friends;
      console.log('Fetched users:', filteredUsers.value);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  } else {
    filteredUsers.value = [];
  }
};

// const toggleUserSelection = (user) => {
//   const index = selectedUsers.value.indexOf(user);
//   if (index === -1) {
//     selectedUsers.value.push(user.searchUserIdx);
//   } else {
//     selectedUsers.value.splice(index, 1);
//   }
// };

const addWorkspace = () => {
  workspaceStore.addWorkspace({
    workspaceName: workspaceName.value,
    participants: selectedUsers.value,
    avatar: 1
  });
  workspaceName.value = '';
  router.push('/my/dashboard');
};
</script>

<template>
  <div class="form-container">
    <div class="workspace-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="workspaceName">WorkSpace 이름</label>
            <input type="text" id="workspaceName" v-model="workspaceName" placeholder="WorkSpace 이름을 입력하세요"
                   class="input-field">
          </div>

          <div>
            <label for="workspaceParticipation">참여자 추가</label>
            <input type="text" id="workspaceParticipation" v-model="participants" placeholder="아이디를 검색해주세요"
                   class="input-field" @keyup.enter="searchUsers">
            <ul v-if="filteredUsers.length">
              <li v-for="user in filteredUsers" :key="user">
                {{ typeof user === 'string' ? user : user.userName }}
              </li>
            </ul>
            <p v-else>검색된 사용자가 없습니다.</p>
          </div>

        </div>
      </div>
      <div class="button-wrap">
        <button @click="addWorkspace" class="add-workspace-btn">WorkSpace 추가</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  width: 100%;
  padding: 20px;
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
  margin-bottom: 5px;
}

.workspace-wrap {
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

.add-workspace-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 20px;
}

.add-workspace-btn:hover {
  background-color: #93AAFD;
}

.selected {
  background-color: #C6D2FD;
}
</style>