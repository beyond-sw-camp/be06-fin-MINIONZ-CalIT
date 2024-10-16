<script setup>
import { computed, inject, ref, watch, onMounted } from 'vue';
import { useTaskStore } from '@/stores/scrum/useTaskStore';
import Multiselect from 'vue-multiselect';
import { useFriendsStore } from '@/stores/user/useFriendsStore';
import { useRoute } from 'vue-router';
import { useSprintStore } from '@/stores/scrum/useSprintStore';
import { timeInputUtils } from "@/utils/timeInputUtils";
import { getLabelColors } from "@/utils/labelUtils";
import { useField, useForm } from 'vee-validate';
import { Notyf } from 'notyf';
import * as yup from 'yup';
import router from "@/router";
import {useTaskLabelStore} from "@/stores/scrum/useTaskLabelStore";
import {useSprintLabelStore} from "@/stores/scrum/useSprintLabelStore";


const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');
contentsTitle.value = '태스크 추가하기';
contentsDescription.value = '태스크를 추가해보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;
const notyf = new Notyf();

const friendStore = useFriendsStore();
const sprintStore = useSprintStore();
const taskStore = useTaskStore();
const taskLabelStore = useTaskLabelStore();

const { handleSubmit, errors } = useForm({
  validationSchema: yup.object({
    selectedSprintId: yup.string().required('스프린트를 선택해주세요'),
    taskName: yup.string().required('Task 제목을 입력해주세요'),
    taskContent: yup.string().required('Task 내용을 입력해주세요'),
    participants: yup.array().min(1, '담당자를 선택해주세요'),
    selectedLevel: yup.string().required('난이도를 선택해주세요'),
    selectedPriority: yup.string().required('중요도를 선택해주세요'),
    startTime: yup.string().required('시작 날짜를 선택해주세요'),
    endTime: yup.string().required('종료 날짜를 선택해주세요')
  })
});

const { value: selectedSprintId } = useField('selectedSprintId');
const { value: taskName } = useField('taskName');
const { value: taskContent } = useField('taskContent');
const { value: participants } = useField('participants', { initialValue: [] });
const { value: selectedLevel } = useField('selectedLevel');
const { value: selectedPriority } = useField('selectedPriority');
const { value: startTime } = useField('startTime');
const { value: endTime } = useField('endTime');


const sprintOptions = ref([]);
const taskSearch = ref('');
const selectedTasks = ref([]);

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

function deleteLabelByName(labelName) {
  const index = labels.value.findIndex(label => label.labelName === labelName);
  if (index !== -1) {
    sprintLabelStore.deleteLabel(index);
    selectedLabel.value = selectedLabel.value.filter(name => name !== labelName);
    labelDetails.value = labelDetails.value.filter(label => label.labelName !== labelName);
  }
}

const onSubmit = handleSubmit(async (values) => {
  try {
    const validatedStartTime = timeInputUtils.validateTime(values.startTime);
    const validatedEndTime = timeInputUtils.validateTime(values.endTime);

    await taskStore.addTask({
      title: values.taskName,
      contents: values.taskContent,
      participants: values.participants.map(participant => participant.searchUserIdx),
      level: values.selectedLevel,
      priority: values.selectedPriority,
      startTime: validatedStartTime,
      endTime: validatedEndTime,
      sprintId: selectedSprintId.value
    });

    taskName.value = '';
    taskContent.value = '';
    participants.value = [];
    selectedLevel.value = '';
    selectedPriority.value = '';
    startTime.value = '';
    endTime.value = '';
    selectedSprintId.value = null;
    selectedTasks.value = [];
    taskSearch.value = '';
    notyf.success('Task가 추가되었습니다.');
    router.push(`/workspace/${workspaceId}/scrum/task/list`);
  } catch (error) {
    notyf.error('Task 추가에 실패했습니다.');
    console.error('Error adding task:', error);
  }
});

const fetchLabels = async () => {
  await taskLabelStore.getTaskLabel(workspaceId);
  availableLabels.value = taskLabelStore.labels;
};

onMounted(() => {
  searchFriends();
  fetchLabels();
  watch(selectedParticipant, (newParticipant) => {
    if (newParticipant) {
      if (!participants.value) {
        participants.value = [];
      }
      if (!participants.value.some(p => p.searchUserIdx === newParticipant.searchUserIdx)) {
        participants.value.push(newParticipant);
      }
    }
  });
});
</script>

<template>
  <div class="form-container">
    <div class="task-wrap">
      <div class="input-wrap">
        <div>
          <label>스프린트 선택하기</label>
          <multiselect
            v-model="selectedSprintId"
            :options="sprintOptions"
            :searchable="true"
            :close-on-select="true"
            :show-labels="false"
            placeholder="스프린트를 선택해주세요"
            label="name"
            track-by="id"
          />
          <p class="error-message" v-if="errors.sprintId">{{ errors.sprintId }}</p>
        </div>
        <div>
          <label for="task-name">Task 제목</label>
          <input type="text" id="task-name" v-model="taskName" placeholder="Task 제목을 적어주세요" class="input-field"/>
          <p class="error-message" v-if="errors.taskName">{{ errors.taskName }}</p>
        </div>
        <div>
          <label for="task-content">Task 내용</label>
          <textarea id="task-content" v-model="taskContent" placeholder="Task 내용을 적어주세요" class="input-field" style="margin: 0"/>
          <p class="error-message" v-if="errors.taskContent">{{ errors.taskContent }}</p>
        </div>
        <div>
          <label>담당자</label>
          <multiselect
            v-model="participants"
            :options="filteredUsers"
            :searchable="true"
            :close-on-select="true"
            :show-labels="false"
            placeholder="담당자를 선택해주세요"
            label="name"
            track-by="id"
          />
          <p class="error-message" v-if="errors.participants">{{ errors.participants }}</p>
          <div class="selections participants" v-if="participants && participants.length">
              <span class="item" v-for="participant in participants" :key="participant.searchUserIdx">
                {{ participant.userName }}
                <span @click="deleteParticipant(participant.searchUserIdx)"
                      style="cursor: pointer; margin: 0 10px; padding: 0">x</span>
              </span>
          </div>
        </div>
        <div class="inputs-wrap">
          <div>
            <div class="time-info">
              <label>시작 날짜</label>
              <span>* 시간 지정은 10분 단위로 저장됩니다.</span>
            </div>
            <input type="datetime-local" id="startDate" v-model="startDate" class="input-field"/>
            <p class="error-message" v-if="errors.startDate">{{ errors.startDate }}</p>
          </div>
          <div>
            <div class="time-info">
              <label>종료 날짜</label>
              <span>* 시간 지정은 10분 단위로 저장됩니다.</span>
            </div>
            <input type="datetime-local" id="endDate" v-model="endDate" class="input-field"/>
            <p class="error-message" v-if="errors.endDate">{{ errors.endDate }}</p>
          </div>
        </div>
        <div>
          <label for="level">난이도</label>
          <multiselect
            v-model="selectedLevel"
            :options="['Easy', 'Medium', 'Hard']"
            placeholder="Level"
          />
          <p class="error-message" v-if="errors.selectedLevel">{{ errors.selectedLevel }}</p>
        </div>
        <div>
          <label for="priority">중요도</label>
          <multiselect
            v-model="selectedPriority"
            :options="['Low', 'Medium', 'High']"
            placeholder="Priority"
          />
          <p class="error-message" v-if="errors.selectedPriority">{{ errors.selectedPriority }}</p>
        </div>
        <div>
          <label>라벨 선택</label>
          <select v-model="selectedLabels" class="input-field">
            <option disabled value="">라벨을 선택하세요</option>
            <option v-for="label in availableLabels" :key="label.labelName" :value="label">
              {{ label.labelName }}
            </option>
          </select>
          <p class="error-message" v-if="errors.sprintLabels">{{ errors.sprintLabels }}</p>
        </div>
        <div v-if="selectedLabel" class="label-details">
          <div v-for="(label, index) in labelDetails" :key="index" class="label-detail-item">
                <span :style="getLabelColors(label)">
                  {{ label.labelName }}
                  <span @click="deleteLabelByName(label.labelName)" style="cursor: pointer; margin: 0 10px; padding: 0">x</span>
                </span>
          </div>
        </div>
      </div>
      <button @click="onSubmit" class="add-task-btn">Task 추가하기</button>
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
  font-weight: 400;
  margin-top: 15px;
  font-size: 16px;
  margin-bottom: 5px;
}

.task-wrap {
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
  background-color: #fff;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
}

.add-task-btn {
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

.add-task-btn:hover {
  background-color: #93aafd;
}

.multiselect__select {
  top: 10px;
}

.multiselect .input-field {
  padding: 0;
  margin-top: 0;
  margin-bottom: 0;
}

.add-workspace-btn:hover {
  background-color: #93AAFD;
}

.error-message {
  background-color: #fff1f1;
  padding: 0.625rem;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
  margin: 0;
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

.time-info {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;

  span {
    font-size: 10px;
    color: #6b7280;
  }
}

.inputs-wrap {
  display: flex;
  gap: 20px;

  div {
    width: 100%;
  }
}

.selections {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
  margin-top: 10px;
}

.item{
  font-size: 12px;
  font-weight: 500;
  color: #606C80;
  padding: 5px 8px;
  border-radius: 15px;
  background-color: #e0f7fa;
}
</style>
