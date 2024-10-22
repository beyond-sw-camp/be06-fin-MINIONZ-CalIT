<script setup>
import { inject, ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useSprintStore } from '@/stores/scrum/useSprintStore';
import { useSprintLabelStore } from '@/stores/scrum/useSprintLabelStore';
import { useFriendsStore } from '@/stores/user/useFriendsStore';
import { timeInputUtils } from '@/utils/timeInputUtils';
import { getLabelColors } from '@/utils/labelUtils';
import { useField, useForm } from 'vee-validate';
import * as yup from 'yup';
import router from '@/router';
import Multiselect from 'vue-multiselect';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = '스프린트 추가하기';
contentsDescription.value = '스프린트를 추가해보세요!';

const route = useRoute();
const workspaceId = route.params.workspaceId;

const sprintStore = useSprintStore();
const sprintLabelStore = useSprintLabelStore();
const friendStore = useFriendsStore();

const { handleSubmit, errors } = useForm({
  validationSchema: yup.object({
    sprintTitle: yup.string().required('스프린트 이름을 입력하세요'),
    sprintContent: yup.string().required('스프린트 내용을 입력하세요'),
    participants: yup.array().min(1, '참여자를 선택하세요'),
    startDate: yup.date().required('시작 날짜를 입력하세요'),
    endDate: yup.date().required('종료 날짜를 입력하세요'),
  }),
});

const { value: sprintTitle } = useField('sprintTitle');
const { value: sprintContent } = useField('sprintContent');
const { value: participants } = useField('participants', { initialValue: [] });
const { value: startDate } = useField('startDate');
const { value: endDate } = useField('endDate');
const { value: labels } = useField('labels', {
  initialValue: [],
});

const filteredFriends = ref([]);
const selectedParticipant = ref(null);
const filteredLabels = ref([]);
const selectedLabel = ref([]);

const searchFriends = async () => {
  try {
    await friendStore.getFriendsList(workspaceId);
    filteredFriends.value = friendStore.friends;
  } catch (error) {
    console.error('Error fetching Friends:', error);
    filteredFriends.value = [];
  }
};

const fetchLabels = async () => {
  try {
    await sprintLabelStore.getSprintLabel(workspaceId);
    filteredLabels.value = sprintLabelStore.labels;
  } catch (error) {
    console.log(error);
    filteredLabels.value = [];
  }
};

const deleteParticipant = (searchUserIdx) => {
  participants.value = participants.value.filter(
    (participant) => participant.searchUserIdx !== searchUserIdx
  );
};

const deleteLabel = (labelId) => {
  labels.value = labels.value.filter((label) => label.labelId !== labelId);
};

const onSubmit = handleSubmit(async (values) => {
  try {
    const validatedStartDate = timeInputUtils.validateTime(values.startDate);
    const validatedEndDate = timeInputUtils.validateTime(values.endDate);

    await sprintStore.addSprint({
      workspaceId: workspaceId,
      sprintTitle: values.sprintTitle,
      sprintContents: values.sprintContent,
      labels: values.labels.map((label) => label.labelId),
      participants: values.participants.map(
        (participant) => participant.searchUserIdx
      ),
      startDate: validatedStartDate,
      endDate: validatedEndDate,
    });

    router.push(`/workspace/${workspaceId}/scrum/sprint/list`);
  } catch (error) {
    console.error('Error adding sprint:', error);
  }
});

onMounted(() => {
  searchFriends();
  fetchLabels();

  watch(selectedParticipant, (newParticipant) => {
    if (newParticipant) {
      if (!participants.value) {
        participants.value = [];
      }
      if (
        !participants.value.some(
          (p) => p.searchUserIdx === newParticipant.searchUserIdx
        )
      ) {
        participants.value.push(newParticipant);
      }
    }
  });

  watch(selectedLabel, (newLabel) => {
    if (newLabel) {
      if (!labels.value) {
        labels.value = [];
      }

      if (!labels.value.some((l) => l.labelId === newLabel.labelId)) {
        labels.value.push(newLabel);
      }
    }
  });
});
</script>

<template>
  <div class="form-container">
    <div class="workspace-wrap">
      <div class="input-wrap">
        <div>
          <div>
            <label for="sprintTitle">Sprint 이름</label>
            <input
              type="text"
              id="sprintTitle"
              v-model="sprintTitle"
              placeholder="스프린트 이름을 입력하세요"
              class="input-field"
            />
            <p class="error-message" v-if="errors.sprintTitle">
              {{ errors.sprintTitle }}
            </p>
          </div>
          <div>
            <label for="sprintContent">Sprint 내용</label>
            <input
              type="text"
              id="sprintContent"
              v-model="sprintContent"
              placeholder="스프린트 내용을 입력하세요"
              class="input-field"
            />
            <p class="error-message" v-if="errors.sprintContent">
              {{ errors.sprintContent }}
            </p>
          </div>

          <div>
            <label for="participants">참여자 선택</label>
            <multiselect
              v-model="selectedParticipant"
              :options="filteredFriends"
              :searchable="true"
              :close-on-select="true"
              :show-labels="false"
              placeholder="참여자를 선택하세요"
              label="userName"
              track-by="searchUserIdx"
            />
            <p class="error-message" v-if="errors.participants">
              {{ errors.participants }}
            </p>
            <div
              class="selections participants"
              v-if="participants && participants.length"
            >
              <span
                class="item"
                v-for="participant in participants"
                :key="participant.searchUserIdx"
              >
                {{ participant.userName }}
                <span
                  @click="deleteParticipant(participant.searchUserIdx)"
                  style="cursor: pointer; margin: 0 10px; padding: 0"
                  >x</span
                >
              </span>
            </div>
          </div>

          <div class="inputs-wrap">
            <div>
              <div class="time-info">
                <label>시작 날짜</label>
                <span>* 시간 지정은 10분 단위로 저장됩니다.</span>
              </div>
              <input
                type="datetime-local"
                id="startDate"
                v-model="startDate"
                class="input-field"
              />
              <p class="error-message" v-if="errors.startDate">
                {{ errors.startDate }}
              </p>
            </div>
            <div>
              <div class="time-info">
                <label>종료 날짜</label>
                <span>* 시간 지정은 10분 단위로 저장됩니다.</span>
              </div>
              <input
                type="datetime-local"
                id="endDate"
                v-model="endDate"
                class="input-field"
              />
              <p class="error-message" v-if="errors.endDate">
                {{ errors.endDate }}
              </p>
            </div>
          </div>

          <!-- 라벨 선택 -->
          <div>
            <label for="labels">라벨 선택</label>
            <multiselect
              v-model="selectedLabel"
              :options="filteredLabels"
              :searchable="true"
              :close-on-select="true"
              :show-labels="false"
              placeholder="라벨을 선택하세요"
              label="labelName"
              track-by="labelId"
            />
            <p class="error-message" v-if="errors.labels">
              {{ errors.labels }}
            </p>
            <div class="selections labels" v-if="labels && labels.length">
              <span class="item" v-for="label in labels" :key="label.labelId">
                {{ label.labelName }}
                <span
                  @click="deleteLabel(label.labelId)"
                  style="cursor: pointer; margin: 0 10px; padding: 0"
                  >x</span
                >
              </span>
            </div>
          </div>

          <div v-if="selectedLabel.length" class="label-details">
            <div
              v-for="(label, index) in selectedLabel"
              :key="index"
              class="label-detail-item"
            >
              <span :style="getLabelColors(label)">
                {{ label.labelName }}
                <span
                  @click="deleteLabelByName(label.labelName)"
                  style="cursor: pointer; margin: 0 10px; padding: 0"
                  >x</span
                >
              </span>
            </div>
          </div>
        </div>
      </div>
      <div class="button-wrap">
        <button @click="onSubmit" class="add-workspace-btn">Sprint 추가</button>
      </div>
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
  background-color: #fff;
  box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.1);
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

.error-message {
  background-color: #fff1f1;
  padding: 0.625rem;
  border-radius: 5px;
  color: red;
  font-size: 0.9em;
  margin: 0;
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

.item {
  font-size: 12px;
  font-weight: 500;
  color: #606c80;
  padding: 5px 8px;
  border-radius: 15px;
  background-color: #e0f7fa;
}
</style>
