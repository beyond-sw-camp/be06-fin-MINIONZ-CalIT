<script setup>
import { ref, computed, defineExpose, defineProps } from 'vue';
import { VTextField, VSelect } from 'vuetify/components';
import { useSprintLabelStore } from '@/stores/scrum/useSprintLabelStore';

const props = defineProps({
  workspaceId: Number,
});

const labelSearch = ref('');
const selectedLabels = ref([]);
const sprintLabelStore = useSprintLabelStore();

const filteredLabels = computed(() => {
  const allLabels = sprintLabelStore.getSprintLabel(props.workspaceId);
  if (!labelSearch.value) {
    return allLabels;
  }
  return allLabels.filter(label => label.labelName.includes(labelSearch.value));
});

const selectLabel = (label) => {
  if (!selectedLabels.value.includes(label)) {
    selectedLabels.value.push(label);
  }
};

defineExpose({
  selectedLabels,
  filteredLabels,
  selectLabel,
});
</script>

<template>
  <div>
    <VTextField
        v-model="labelSearch"
        placeholder="label을 검색해주세요"
        class="input-field"
        @keyup.enter="selectLabel({ labelName: labelSearch, labelId: Date.now() })"
    />
    <VSelect
        v-model="selectedLabels"
        :items="filteredLabels"
        item-text="labelName"
        item-value="labelId"
        label="라벨 추가하기"
        multiple
        chips
        clearable
        @change="selectLabel"
    />
    <ul>
      <li v-for="label in selectedLabels" :key="label.labelId">
        {{ label.labelName }}
      </li>
    </ul>
  </div>
</template>