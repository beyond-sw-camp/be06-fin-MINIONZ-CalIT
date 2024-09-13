<script setup>
import { computed, defineProps } from 'vue';
import RightSideIssue from '@/view/scrum/meeting/component/RightSide/RightSideIssue.vue';
import RightSideLabel from '@/view/scrum/meeting/component/RightSide/RightSideLabel.vue';
import RightSideParticipants from '@/view/scrum/meeting/component/RightSide/RightSideParticipants.vue';
import RightSideTask from '@/view/scrum/meeting/component/RightSide/RightSideTask.vue';

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
      return RightSideLabel;
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
  //padding: 0 10px;
  background-color: #F3F6FF;
  //width: 100%;
  box-sizing: border-box;
  /* margin: 0 auto; */
  padding: 20px;
  //border: 1px solid #ddd;
  border-radius: 8px;
  //background-color: #fff;
}
</style>