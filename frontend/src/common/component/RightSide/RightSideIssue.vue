<script setup>
import {computed, ref} from 'vue';
import Multiselect from 'vue-multiselect';
import {useFriendsStore} from "@/stores/friends/useFriendsStore";
import {useRoute} from "vue-router";
import {useIssueStore} from "@/stores/workspace/scrum/useIssueStore";

const route = useRoute();
const workspaceId = route.params.workspaceId;

const issueStore = useIssueStore();
const issueName = ref('');
const issueContent = ref('');
const startTime = ref('');
const endTime = ref('');
const assignees = ref([]);
const friendStore = useFriendsStore();
const filteredUsers = computed(() => {
  return friendStore.getFriendsList().filter(user => !assignees.value.includes(user));
});

const addIssue = () => {
  issueStore.addIssue({
    issueName: issueName.value,
    issueContent: issueContent.value,
    assignees: assignees.value,
    startTime: startTime.value,
    endTime: endTime.value,
  });
  issueName.value = '';
  issueContent.value = '';
  startTime.value = '';
  endTime.value = '';
  assignees.value = [];
};
</script>

<template>
  <div class="form-container">
    <h2>Issue 추가하기</h2>
    <hr/>
    <div class="issue-wrap">
      <div class="input-wrap">
    <!-- Issue 이름 입력 필드 -->
        <div>
          <label for="issue-name">Issue 이름</label>
          <input type="text" id="issue-name" v-model="issueName" placeholder="Issue 이름을 적어주세요" class="input-field"/>
        </div>

        <div>
          <label for="issue-content">Issue 내용</label>
          <textarea id="issue-content" v-model="issueContent" placeholder="Issue 내용을 적어주세요" class="input-field" style="margin:0"/>
        </div>

        <div class="time-wrap">
          <label>시작 날짜</label>
          <input v-model="startTime" type="datetime-local" class="time-editor" />
          <span>~ 종료 날짜</span>
          <input v-model="endTime" type="datetime-local" class="time-editor" />
        </div>

        <div>
          <label>담당자</label>
          <input type="text" placeholder="사용자를 검색해주세요" class="input-field">
        </div>
  </div>
    <!-- 추가 버튼 -->
    <button @click="addIssue" class="add-issue-btn">Add Issue</button>
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

label {
  display: block;
  font-weight: 400;
  margin-top: 15px;
  font-size: 16px;
}

hr{
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

.issue-wrap{
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
}

.add-issue-btn:hover {
  background-color: #93AAFD;
}

.multiselect .input-field {
  padding: 0;
  margin-top: 0;
  margin-bottom: 0;
}

.time-wrap{
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-direction: column;
}

.time-editor{
  border: none;
  width: 200px;
}
</style>