<script setup>
import {onMounted, ref} from 'vue';
import {useIssueStore} from '@/stores/scrum/useIssueStore';
import {useFriendsStore} from '@/stores/user/useFriendsStore';
import {useRoute} from 'vue-router';
import {timeInputUtils} from '@/utils/timeInputUtils';
import {useField, useForm} from 'vee-validate';
import {Notyf} from 'notyf';
import * as yup from 'yup';

const notyf = new Notyf();
const route = useRoute();
const workspaceId = route.params.workspaceId;

const issueStore = useIssueStore();
const friendsStore = useFriendsStore();

const {handleSubmit, errors} = useForm({
  validationSchema: yup.object({
    issueName: yup.string().required('Issue 이름을 입력하세요'),
    issueDescription: yup.string().required('Issue 설명을 입력하세요'),
    issueManager: yup.object().required('이슈 매니저를 선택하세요'),
    startDate: yup.date().required('시작 날짜를 입력하세요'),
    endDate: yup.date().required('종료 날짜��� 입력하세요'),
  }),
});

const {value: issueName} = useField('issueName');
const {value: issueDescription} = useField('issueDescription');
const {value: issueManager} = useField('issueManager');
const {value: startDate} = useField('startDate');
const {value: endDate} = useField('endDate');

const filteredFriends = ref([]);

const searchFriends = async () => {
  try {
    await friendsStore.getFriendsList(workspaceId);
    filteredFriends.value = friendsStore.friends;
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

const onSubmit = handleSubmit(async (values) => {
  try {
    const validatedStartDate = timeInputUtils.validateTime(values.startDate);
    const validatedEndDate = timeInputUtils.validateTime(values.endDate);

    await issueStore.addIssue(workspaceId, {
      title: values.issueName,
      contents: values.issueDescription,
      managerId: issueManager.value.searchUserIdx,
      startDate: validatedStartDate,
      endDate: validatedEndDate,
    });

    issueName.value = '';
    issueDescription.value = '';
    issueManager.value = null;
    startDate.value = '';
    endDate.value = '';

    notyf.success('이슈가 성공적으로 추가되었습니다.');
  } catch (error) {
    notyf.error('이슈 추가에 실패하였습니다.');
  }
});

onMounted(() => {
  filteredFriends.value = [];
  searchFriends();
});
</script>

<template>
  <div class="form-container">
    <h2>Issue 추가하기</h2>
    <hr/>
    <div class="issue-wrap">
      <div class="input-wrap">
        <div>
          <label for="issue-name">Issue 이름</label>
          <input type="text" id="issue-name" v-model="issueName" placeholder="Issue 이름을 적어주세요" class="input-field"/>
          <p class="error-message" v-if="errors.issueName">{{ errors.issueName }}</p>
        </div>

        <div>
          <label for="issueDescription">Issue 설명</label>
          <textarea id="issueDescription" v-model="issueDescription" placeholder="Issue의 설명을 넣어주세요"
                    class="input-field"></textarea>
          <p class="error-message" v-if="errors.issueDescription">{{ errors.issueDescription }}</p>
        </div>

        <div class="time-wrap">
          <label>시작 날짜</label>
          <input v-model="startDate" type="datetime-local" class="time-editor" step="600"/>
          <p class="error-message" v-if="errors.startDate">{{ errors.startDate }}</p>
          <span>~ 종료 날짜</span>
          <input v-model="endDate" type="datetime-local" class="time-editor" step="600"/>
          <p class="error-message" v-if="errors.endDate">{{ errors.endDate }}</p>
        </div>

        <div>
          <label for="issueManager">이슈 매니저 할당</label>
          <select id="issueManager" v-model="issueManager" class="input-field">
            <option disabled value="">이슈 매니저를 선택하세요</option>
            <option v-for="friend in filteredFriends" :key="friend.searchFriendsIdx" :value="friend">
              {{ friend.userName || '할당할 사용자를 추가해주세요' }}
            </option>
          </select>
          <p class="error-message" v-if="errors.issueManager">{{ errors.issueManager }}</p>
        </div>
      </div>
      <div class="button-wrap">
        <button @click="onSubmit" class="add-issue-btn">Issue 추가</button>
      </div>
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

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
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
}

.add-issue-btn:hover {
  background-color: #93AAFD;
}

.multiselect .input-field {
  padding: 0;
  margin-top: 0;
  margin-bottom: 0;
}

.time-wrap {
  display: flex;
  gap: 10px;
  justify-content: center;
  flex-direction: column;
}

.time-editor {
  border: none;
  width: 200px;
}

.error-message {
  background-color: #fff1f1;
  padding: 0.625rem;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
  margin: 0;
}
</style>