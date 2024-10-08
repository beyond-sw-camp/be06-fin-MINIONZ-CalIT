<script setup>
import { ref } from 'vue';
import { useLabelStore } from '@/stores/scrum/useLabelStore';

const { getLabels, addLabel, deleteLabel } = useLabelStore();

const selectedLabel = ref([]);
const labelDetails = ref([]);

const labels = ref((typeof getLabels === 'function' ? getLabels() : []) || []);
const labelColor = ref('');
const labelName = ref('');
const labelDescription = ref('');
const selectedLabelName = ref('');

function showLabelDetails() {
  if (selectedLabelName.value && !selectedLabel.value.includes(selectedLabelName.value)) {
    selectedLabel.value.push(selectedLabelName.value);
    const label = labels.value.find(label => label.labelName === selectedLabelName.value);
    if (label) {
      labelDetails.value.push(label);
    }
  }
}

function deleteLabelByName(labelName) {
  const index = labels.value.findIndex(label => label.labelName === labelName);
  if (index !== -1) {
    deleteLabel(index);
    selectedLabel.value = selectedLabel.value.filter(name => name !== labelName);
    labelDetails.value = labelDetails.value.filter(label => label.labelName !== labelName);
  }
}

function addNewLabel() {
  if (labelName.value && labelDescription.value && labelColor.value) {
    addLabel({
      labelName: labelName.value,
      description: labelDescription.value,
      color: {
        backgroundColor: labelColor.value
      }
    });
    labelName.value = '';
    labelDescription.value = '';
    labelColor.value = '';
  }
}
</script>

<template>
  <div class="form-container">
    <h2>라벨 추가하기</h2>
    <hr/>
    <div class="label-wrap">
      <div class="input-wrap">
        <div>
          <div class="input-wrap-item">
            <label for="label-select">기존 라벨 선택</label>
            <select id="label-select" v-model="selectedLabelName" class="input-field" @change="showLabelDetails">
              <option disabled value="">선택하기</option>
              <option v-for="label in labels" :key="label.labelName" :value="label.labelName">
                {{ label.labelName }}
              </option>
            </select>
          </div>
          <div>
            <div v-if="selectedLabelName" class="label-details">
              <div v-for="(label, index) in labelDetails" :key="index" class="label-detail-item">
                <span :style="{ backgroundColor: label.color.backgroundColor, color: label.color.color }">
                  {{ label.labelName }}
                  <span @click="deleteLabelByName(label.labelName)" style="cursor: pointer; margin-left: 10px;">x</span>
                </span>
              </div>
            </div>
          </div>
        </div>

        <h3>라벨 생성하기</h3>
        <div class="input-wrap-item">
          <label for="label-name">라벨 명</label>
          <input type="text" id="label-name" v-model="labelName" placeholder="Name label" class="input-field"/>
        </div>

        <div class="input-wrap-item">
          <label for="label-description">라벨 설명</label>
          <input
              type="text"
              id="label-description"
              v-model="labelDescription"
              placeholder="Description label"
              class="input-field"
          />
        </div>

        <div class="input-wrap-item">
          <label for="label-color">라벨 색상</label>
          <input
              type="color"
              id="label-color"
              v-model="labelColor"
              class="input-field"
          />
        </div>
      </div>
      <!-- 추가 버튼 -->
      <button @click="addNewLabel" class="add-label-btn">라벨 추가하기</button>
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

h3 {
  font-size: 18px;
  font-weight: 500;
  margin: 10px 0 0;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

label {
  display: block;
  font-weight: 400;
  margin-top: 10px;
  font-size: 16px;
}

.label-wrap {
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
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.add-label-btn {
  background-color: #C6D2FD;
  color: #28303F;
  padding: 10px;
  width: 100%;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

.add-label-btn:hover {
  background-color: #93AAFD;
}

.label-detail-item {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 10px;

  span {
    padding: 5px;
    border-radius: 15px;
  }
}
</style>