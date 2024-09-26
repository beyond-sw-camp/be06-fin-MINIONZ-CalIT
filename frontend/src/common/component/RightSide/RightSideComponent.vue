<script setup>
import {computed, defineProps} from 'vue';
import RightSideIssue from '@/common/component/RightSide/RightSideIssue.vue';
// import RightSideLabel from '@/view/scrum/meeting/component/RightSide/RightSideLabel.vue';
import RightSideParticipants from '@/common/component/RightSide/RightSideParticipants.vue';
import RightSideTask from '@/common/component/RightSide/RightSideTask.vue';

const props = defineProps({
  activeComponentId: {
    type: String,
    required: true
  }
});

const activeComponent = computed(() => {
  switch (props.activeComponentId) {
    case 'issue':
      return RightSideIssue;
    case 'label':
      return RightSideIssue;
    case 'participants':
      return RightSideParticipants;
    case 'task':
      return RightSideTask;
    default:
      return null;
  }
});
</script>

<template>
  <div class="right-side">
    <component :is="activeComponent"/>
  </div>
</template>

<style scoped>
.right-side {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 500px;
  box-sizing: border-box;
  padding: 20px;
  margin-right: 30px;
  border-radius: 8px;
}
</style>