<script setup>
import { inject, ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useIssueStore } from '@/stores/scrum/useIssueStore';
import { useFriendsStore } from '@/stores/user/useFriendsStore';
import { timeInputUtils } from "@/utils/timeInputUtils";
import { useField, useForm } from 'vee-validate';
import { Notyf } from 'notyf';
import * as yup from 'yup';
import router from "@/router";

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'Issue 추가하기';
contentsDescription.value = 'Issue를 추가해보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const issueStore = useIssueStore();
const friendStore = useFriendsStore();
const notyf = new Notyf();

const { handleSubmit, errors } = useForm({
  validationSchema: yup.object({
    issueName: yup.string().required('Issue 이름을 입력하세요'),
    issueDescription: yup.string().required('Issue 설명을 입력하세요'),
    issueManager: yup.object().required('이슈 매니저를 선택하세요'),
    startDate: yup.date().required('시작 날짜를 입력하세요'),
    endDate: yup.date().required('종료 날짜를 입력하세요'),
  }),
});

const { value: issueName } = useField('issueName');
const { value: issueDescription } = useField('issueDescription');
const { value: issueManager } = useField('issueManager');
const { value: startDate } = useField('startDate');
const { value: endDate } = useField('endDate');

const filteredFriends = ref([]);

const searchFriends = async () => {
  try {
    await friendStore.getFriendsList(workspaceId);
    filteredFriends.value = friendStore.friends;
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
    router.push(`/workspace/${workspaceId}/scrum/issue/list`);
  } catch (error) {
    notyf.error('이슈 추가에 실패하였습니다.');
  }
});

onMounted(searchFriends);
</script>

<template>
  <div class="form-container">
    <div class="issue-wrap">
      <div class="input-wrap">
        <div class="inputs-wrap">
          <div>
            <label for="issueName">Issue 이름</label>
            <input type="text" id="issueName" v-model="issueName" placeholder="Issue 이름을 입력하세요" class="input-field" />
            <p class="error-message" v-if="errors.issueName">{{ errors.issueName }}</p>
          </div>
          <div>
            <label for="issueManager">이슈 매니저 할당</label>
            <select id="issueManager" v-model="issueManager" class="input-field">
              <option disabled value="">이슈 매니저를 선택하세요</option>
              <option v-for="friend in filteredFriends" :key="friend.searchFriendsIdx" :value="friend">
                {{ friend.userName || '할당할 사용자를 추가해주세요' }}
              </option>
            </select>
            <p class="error-message" v-if="errors.issueManager">{{errors.issueManager}}</p>
          </div>
        </div>

        <div>
          <label for="issueDescription">Issue 설명</label>
          <textarea id="issueDescription" v-model="issueDescription" placeholder="Issue의 설명을 넣어주세요" class="input-field"></textarea>
          <p class="error-message" v-if="errors.issueDescription">{{ errors.issueDescription }}</p>
        </div>


      </div>

      <div class="inputs-wrap">
        <div>
          <div class="time-info">
            <label>시작 날짜</label>
            <span>* 시간 지정은 10분 단위로 저장됩니다.</span>
          </div>
          <input type="datetime-local" v-model="startDate" class="input-field" step="600" />
          <p class="error-message" v-if="errors.startDate">{{ errors.startDate }}</p>
        </div>
        <div>
          <div class="time-info">
            <label>종료 날짜</label>
            <span>* 시간 지정은 10분 단위로 저장됩니다.</span>
          </div>
          <input type="datetime-local" v-model="endDate" class="input-field" step="600" />
          <p class="error-message" v-if="errors.endDate">{{ errors.endDate }}</p>
        </div>
      </div>

    </div>
    <div class="button-wrap">
      <button @click="onSubmit" class="add-issue-btn">Issue 추가</button>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  margin: 30px;
  box-sizing: border-box;
  padding: 20px 40px;
  border-radius: 10px;
  background-color: #f7f8fa;
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
  font-weight: 500;
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
  border-radius: 5px;
  font-size: 1rem;
  background-color: #fff;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
}

.add-issue-btn {
  background-color: #c6d2fd;
  color: #28303f;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 20px;
  font-weight: 500;
}

.add-issue-btn:hover {
  background-color: #93aafd;
  color: #fff;
}

.error-message {
  background-color: #fff1f1;
  padding: 0.625rem;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
  margin: 0;
}

.time-info{
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  span{
    font-size: 10px;
    color: #6b7280;
  }
}

.inputs-wrap{
  display: flex;
  gap: 20px;
  div{
    width: 100%;
  }
}
</style>
