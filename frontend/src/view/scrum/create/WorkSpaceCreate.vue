<script setup>
import {inject, onMounted, ref} from 'vue';
import {useWorkspaceStore} from '@/stores/workspace/space/useWorkspaceStore';
import {useFriendsStore} from '@/stores/friends/useFriendsStore';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'WorkSpace 추가하기';
contentsDescription.value = 'WorkSpace를 추가해보세요!';

const workspaceStore = useWorkspaceStore();
const friendStore = useFriendsStore();
const workspaceName = ref('');
const participants = ref('');
const filteredUsers = ref([]);

onMounted(async () => {
  const users = await friendStore.getUserList();
  if (users && Array.isArray(users)) {
    filteredUsers.value = users.filter(user => user && user.includes(participants.value || ''));
  } else {
    filteredUsers.value = [];
  }
});

const searchUsers = async () => {
  if (participants.value) {
    filteredUsers.value = (await friendStore.getUserList(participants.value)).filter(user => user && user.includes(participants.value || ''));
  } else {
    filteredUsers.value = [];
  }
};

const addWorkspace = () => {
  workspaceStore.addWorkspace({
    workspaceName: workspaceName.value,
  });
  workspaceName.value = '';
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
            <input type="text" id="workspaceParticipation" v-model="participants" placeholder="참여자를 검색해주세요"
                   class="input-field" @keyup.enter="searchUsers">
            <ul v-if="filteredUsers.length">
              <li v-for="user in filteredUsers" :key="user">{{ user }}</li>
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
</style>