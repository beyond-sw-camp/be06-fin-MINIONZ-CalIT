<script setup>
import {computed, inject, ref} from 'vue';
import { issueData } from "@/static/issueData";
// import { useIssueStore } from '@/stores/workspace/scrum/useIssueStore';
import { useFriendsStore } from '@/stores/friends/useFriendsStore';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Issue 추가하기';
contentsDescription.value = 'Issue를 추가해보세요!';

const issueStore = issueData;
const friendStore = useFriendsStore();
const issueName = ref('');
const participants = ref('');

const filteredUsers = computed(() => {
  return friendStore.getUserList().filter(user => {
    return user.includes(participants.value);
  });
});

const addIssue = () => {
  issueStore.addIssue({
    issueName: issueName.value,
  });
  issueName.value = '';
};
</script>

<template>
  <div class="form-container">
    <div class="issue-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="issueName">Issue 이름</label>
            <input type="text" id="issueName" v-model="issueName" placeholder="Issue 이름을 입력하세요">
          </div>

          <div>
            <label for="issueParticipation">참여자 추가</label>
            <input type="text" id="issueParticipation" v-model="participants" placeholder="참여자를 검색해주세요" class="input-field">
            <ul>
              <li v-for="user in filteredUsers" :key="user">{{ user }}</li>
            </ul>
          </div>
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