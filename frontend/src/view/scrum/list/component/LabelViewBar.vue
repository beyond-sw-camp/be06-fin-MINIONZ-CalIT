<script setup>
import { ref } from 'vue';

const props = defineProps({
  currentView: String
});

const emit = defineEmits(['view-change']);

const setView = (view) => {
  emit('view-change', view);
};
</script>

<template>
  <div class="view-toggle-bar">
    <div class="view-toggle-buttons">
      <div :class="{ active: props.currentView === 'sprint' }" @click="setView('sprint')">
        <i class="icon-sprint"></i>
        Sprint
      </div>
      <div class="v-line"/>
      <div :class="{ active: props.currentView === 'task' }" @click="setView('task')">
        <i class="icon-task"></i>
        Task
      </div>
      <div class="v-line"/>
      <div :class="{ active: props.currentView === 'meeting' }" @click="setView('meeting')">
        <i class="icon-meeting"></i>
        Meeting
      </div>
    </div>
  </div>
</template>

<style scoped>
.input-field {
  width: 200px;
  padding: 10px;
  margin-top: 10px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  box-sizing: border-box;
}
i{
  width: 24px;
  display: block;
  height: 24px;
}
a{
  display: flex;
  text-decoration: none;
  color: #28303F;
  border-radius: 4px;
  margin: 5px 10px;
  padding: 5px;
  &:hover, &:focus{
    background-color: #C6D2FD;
  }
}
.icon-sprint{
  background-image: url("@/assets/icon/menu/sprint.svg");
}
.icon-task{
  background-image: url("@/assets/icon/menu/task.svg");
}
.icon-meeting{
  background-image: url("@/assets/icon/menu/meeting.svg");
}
.view-toggle-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px 30px;
}

.view-toggle-buttons {
  display: flex;
  align-items: center;
  background-color: #F3F6FF;
  border-radius: 4px;
  margin-top: 10px;
  padding: 10px;
  div{
    display: flex;
  }
}

.view-toggle-buttons button {
  background: none;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 10px 20px;
  margin-right: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.view-toggle-buttons button.active {
  background-color: #e0f7fa;
  border-color: #00bcd4;
}

.view-toggle-buttons i {
  margin-right: 8px;
}

.avatar-group {
  display: flex;
  align-items: center;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-left: -10px;
  border: 2px solid white;
}

.more-avatars {
  background-color: #f0f0f0;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: -10px;
  font-size: 14px;
}

.add-avatar-btn {
  background-color: transparent;
  border: 2px solid #e0e0e0;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 10px;
  cursor: pointer;
  color: #00bcd4;
  font-size: 24px;
}

.v-line {
  border-left : 1px solid #93AAFD;
  height : 24px;
  margin: 0 10px;
}
</style>