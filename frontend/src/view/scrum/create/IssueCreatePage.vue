<script setup>
import { inject, ref, onMounted } from 'vue';
import { useRouter } from "vue-router";
import { useIssueStore } from '@/stores/scrum/useIssueStore';
import { useFriendsStore } from '@/stores/user/useFriendsStore';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Issue 추가하기';
contentsDescription.value = 'Issue를 추가해보세요!';

const router = useRouter();
const workspaceId = router.currentRoute.value.params.workspaceId;

const issueStore = useIssueStore();
const friendStore = useFriendsStore();

const issueName = ref('');
const issueDescription = ref('');
const issueManager = ref(null);
const issueAssignee = ref(null);
const issueReviewer = ref(null);
const startDate = ref('');
const endDate = ref('');
const filteredFriends = ref([]);

const fetchUsers = async () => {
  try {
    filteredFriends.value = await friendStore.getFriendsList(workspaceId);
    console.log(filteredFriends.value);
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

const addIssue = () => {
  issueStore.addIssue({
    issueName: issueName.value,
    issueDescription: issueDescription.value,
    issueManager: issueManager.value,
    issueAssignee: issueAssignee.value,
    issueReviewer: issueReviewer.value,
    startDate: startDate.value,
    endDate: endDate.value,
  });
  issueName.value = '';
  issueDescription.value = '';
  issueManager.value = null;
  issueAssignee.value = null;
  issueReviewer.value = null;
  startDate.value = '';
  endDate.value = '';
};

onMounted(fetchUsers);
</script>

<template>
  <div class="form-container">
    <div class="issue-wrap">
      <div class="input-wrap">
        <div>
          <label for="issueName">Issue 이름</label>
          <input type="text" id="issueName" v-model="issueName" placeholder="Issue 이름을 입력하세요" class="input-field">
        </div>
        <div>
          <label for="issueDescription">Issue 설명</label>
          <input type="text" id="issueDescription" v-model="issueDescription" placeholder="Issue의 설명을 넣어주세요"
                 class="input-field">
        </div>

        <div>
          <label for="issueManager">이슈 매니저 할당</label>
          <select id="issueManager" v-model="issueManager" class="input-field">
            <option v-for="user in filteredFriends" :key="user.id" :value="user">{{ user.name }}</option>
          </select>
        </div>

        <div>
          <label for="issueAssignee">담당자 추가</label>
          <select id="issueAssignee" v-model="issueAssignee" class="input-field">
            <option v-for="user in filteredFriends" :key="user.id" :value="user">{{ user.name }}</option>
          </select>
        </div>

        <div>
          <label for="issueReviewer">리뷰어 추가</label>
          <select id="issueReviewer" v-model="issueReviewer" class="input-field">
            <option v-for="user in filteredFriends" :key="user.id" :value="user">{{ user.name }}</option>
          </select>
        </div>

        <div>
          <label>시작 날짜</label>
          <input type="datetime-local" v-model="startDate" class="input-field">
        </div>

        <div>
          <label>종료 날짜</label>
          <input type="datetime-local" v-model="endDate" class="input-field">
        </div>
      </div>
      <div class="button-wrap">
        <button @click="addIssue" class="add-issue-btn">Issue 추가</button>
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

.issue-wrap {
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

.add-issue-btn {
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

.add-issue-btn:hover {
  background-color: #93AAFD;
}
</style>