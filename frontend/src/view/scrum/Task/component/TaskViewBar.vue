<script setup>
import { ref, watch, onMounted, defineEmits } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useSprintStore } from '@/stores/scrum/useSprintStore';
import { useTaskStore } from '@/stores/scrum/useTaskStore';

const route = useRoute();
const router = useRouter();
const showAvatarGroup = ref(true);
const workspaceId = route.params.workspaceId;

watch(
    () => route.path,
    (newPath) => {
      showAvatarGroup.value = !newPath.startsWith('/my');
    },
    { immediate: true }
);

const currentView = ref('board');

const setView = (view) => {
  currentView.value = view;
  const basePath = route.path.split('/').slice(0, -1).join('/');
  router.push(`${basePath}/${view}`);
};

const to = ref('kanban');

const sprintStore = useSprintStore();
const taskStore = useTaskStore();
const sprintOptions = ref([]);
const taskLists = ref([]);
const selectedSprintId = ref(null);

onMounted(async () => {
  await sprintStore.getSprintList(workspaceId);
  sprintOptions.value = sprintStore.sprints;

  if (sprintOptions.value.length > 0) {
    selectedSprintId.value = sprintOptions.value[0].sprintId;
    await getTasks(selectedSprintId.value);
  }
});

const getTasks = async (sprintId) => {
  sprintStore.setNowSprintId(sprintId);
  taskLists.value = await taskStore.getTaskList(workspaceId, sprintId);
};

const emit = defineEmits(['update-kanban-cards']);

const emitGetTaskListByUser = async () => {
  const taskStore = useTaskStore();
  const tasks = await taskStore.getTaskListByUser();
  emit('update-kanban-cards', tasks);
};

watch(selectedSprintId, async (newSprintId) => {
  if (newSprintId) {
    sprintStore.setNowSprintId(newSprintId);
    taskLists.value = await taskStore.getTaskList(workspaceId, newSprintId);
  }
});
</script>

<template>
  <div class="view-toggle-bar">
    <select v-model="selectedSprintId" class="input-field">
      <option
          v-for="sprint in sprintOptions"
          :key="sprint.sprintId"
          :value="sprint.sprintId"
      >
        {{ sprint.title }}
      </option>
    </select>
    <div v-if="showAvatarGroup" class="avatar-group">
      <img v-for="(avatar, index) in avatars" :key="index" :src="avatar.src" :alt="avatar.alt" class="avatar" @click="emitGetTaskListByUser"/>
    </div>
    <div class="view-toggle-buttons">
      <router-link
          :to="'kanban'"
          :class="{ active: currentView === 'kanban' }"
          @click="setView('kanban')"
      >
        <i class="icon-kanban"></i>
        Kanban
      </router-link>
      <div class="v-line" />
      <router-link
          :to="'list'"
          :class="{ active: currentView === 'list' }"
          @click="setView('list')"
      >
        <i class="icon-list"></i>
        List
      </router-link>
      <div class="v-line" />
      <router-link
          :to="
          to.startsWith('my')
            ? `/my/task/${currentView}`
            : to.startsWith('workspace')
            ? `/workspace/task/${currentView}`
            : to
        "
          :class="{ active: currentView === 'timeline' }"
          @click="setView('timeline')"
      >
        <i class="icon-timeline"></i>
        TimeLine
      </router-link>
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
i {
  width: 24px;
  display: block;
  height: 24px;
}
a {
  display: flex;
  text-decoration: none;
  //padding: 10px;
  color: #28303f;
  border-radius: 4px;
  margin: 5px 10px;
  padding: 5px;
  &:hover,
  &:focus {
    background-color: #c6d2fd;
  }
}
.icon-kanban {
  background-image: url('@/assets/icon/menu/kanban.svg');
}
.icon-list {
  background-image: url('@/assets/icon/menu/list.svg');
}
.icon-timeline {
  background-image: url('@/assets/icon/menu/timeline.svg');
}
.view-toggle-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  //padding: 10px;
  margin: 10px 30px;
}

.view-toggle-buttons {
  display: flex;
  align-items: center;
  //border: 1px solid #cccccc;
  background-color: #f3f6ff;
  border-radius: 4px;
  margin-top: 10px;
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
  border-left: 1px solid #93aafd;
  height: 24px;
  margin: 0 10px;
}
</style>