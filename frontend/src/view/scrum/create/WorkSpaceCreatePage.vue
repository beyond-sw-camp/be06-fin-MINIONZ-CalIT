<script setup>
import { inject, ref } from 'vue';
import { useFriendsStore } from '@/stores/user/useFriendsStore';
import { axiosInstance } from '@/utils/axiosInstance';
import { Notyf } from 'notyf';
import { useRouter } from 'vue-router';
import { setPersona } from '@/utils/personaUtils';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'WorkSpace 추가하기';
contentsDescription.value = 'WorkSpace를 추가해보세요!';
const notyf = new Notyf();
const router = useRouter();

const friendStore = useFriendsStore();
const workspaceName = ref('');
const participantsInput = ref('');
const filteredUsers = ref([]);
const selectedUsers = ref([]);

const searchUsers = async () => {
  if (participantsInput.value) {
    try {
      const getUserListResult = await friendStore.getUserList(
        participantsInput.value
      );
      filteredUsers.value = [...filteredUsers.value, ...getUserListResult];
    } catch (error) {
      filteredUsers.value = [];
    }
  } else {
    filteredUsers.value = [];
  }
};

const toggleUserSelection = (user) => {
  const index = selectedUsers.value.indexOf(user.searchUserIdx);
  if (index === -1) {
    selectedUsers.value.push(user.searchUserIdx);
  } else {
    selectedUsers.value.splice(index, 1);
  }
};

const isSelected = (user) => {
  return selectedUsers.value.includes(user.searchUserIdx);
};

const addWorkspace = async ({ workspaceName, participants }) => {
  try {
    const response = await axiosInstance.post('/api/workspace', {
      workspaceName,
      participants,
    });
    notyf.success('WorkSpace가 추가되었습니다.');
    await router.push('/my/dashboard');
    return response.data;
  } catch (error) {
    notyf.error('WorkSpace 추가에 실패했습니다.');
    throw error;
  }
};
</script>

<template>
  <div class="form-container">
    <div class="workspace-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="workspaceName">WorkSpace 이름</label>
            <input
              type="text"
              id="workspaceName"
              v-model="workspaceName"
              placeholder="WorkSpace 이름을 입력하세요"
              class="input-field"
            />
          </div>

          <div>
            <label for="workspaceParticipation">참여자 추가</label>
            <input
              type="text"
              id="workspaceParticipation"
              v-model="participantsInput"
              placeholder="아이디를 검색해주세요"
              class="input-field"
              @keyup.enter="searchUsers"
            />
            <ul v-if="filteredUsers.length > 0">
              <li
                v-for="user in filteredUsers"
                :key="user.searchUserIdx"
                @click="toggleUserSelection(user)"
                :class="{ selected: isSelected(user) }"
                :style="{ order: isSelected(user) ? -1 : 0 }"
                style="
                  /* justify-content: center; */
                  align-items: center;
                  display: flex;
                "
              >
                <img :src="setPersona(user.persona)"
                  alt="persona"
                  style="width: 30px; height: 30px; margin-right: 20px"
                />
                <span class="participant-name">{{
                  typeof user === 'string' ? user : user.userName
                }}</span>
              </li>
            </ul>
            <p v-else-if="filteredUsers === null">검색된 사용자가 없습니다.</p>
          </div>
        </div>
      </div>
      <div class="button-wrap">
        <button
          @click="
            addWorkspace({
              workspaceName: workspaceName,
              participants: selectedUsers,
            })
          "
          class="add-workspace-btn"
        >
          WorkSpace 추가
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
ul {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
ul,
li {
  list-style: none;
  padding: 0;
  margin: 0;
}

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
  background-color: #c6d2fd;
  color: #28303f;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 20px;
}

.add-workspace-btn:hover {
  background-color: #93aafd;
}

li {
  padding: 10px;
  cursor: pointer;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.selected {
  background-color: #c6d2fd;
  border: none;
}
</style>
