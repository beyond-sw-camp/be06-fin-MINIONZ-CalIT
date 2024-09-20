<script setup>
import user1 from '@/assets/icon/persona/user1.svg';
// import user2 from '@/assets/icon/persona/user2.svg';
// import user3 from '@/assets/icon/persona/user3.svg';
import plus from '@/assets/icon/menu/plus.svg';
// import { useRouter } from 'vue-router';
// import { defineProps } from 'vue';
import { workspaceList } from '@/stores/workspace/useWorkspaceStore';

// const router = useRouter();

const workspaceInfo = workspaceList;
</script>

<template>
  <div class="workspace-modal">
    <div class="modal-wrap">
    <div class="workspace-modal-header">
      <div>
        <p>Workspace List</p>
      </div>
      <hr>
    </div>
    <div class="workspace-modal-body">
      <ul>
        <li>
          <div class="workspace-item">
            <router-link to="/my/dashboard">
              <img :src="user1" alt="user">
              <p>My Space</p>
            </router-link>
          </div>
          <hr>
        </li>
        <li v-for="(workspace) in workspaceInfo" :key="workspace.workspaceId">
          <div class="workspace-item">
          <router-link :to="'/workspace/' + workspace.workspaceId + '/dashboard'">
            <img :src="workspace.persona" alt="workspace1">
            <p>{{ workspace.workspaceName }}</p>
          </router-link>
          </div>
        </li>
      </ul>
    </div>
    <div class="workspace-modal-footer">
      <hr>
      <div>
        <router-link to="/workspace/scrum/workspace">
          <img :src="plus" class="plus" alt="plus-btn">
          <p>Add Workspace</p>
        </router-link>
      </div>
    </div>
  </div>
  </div>
</template>

<style scoped>
.workspace-modal {
  background-color: #F3F6FF;
  padding: 16px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  position: absolute;
  top: 50px;
  right: 10px;
  width: 180px;
}

.modal-wrap {
  position: relative;
}

.workspace-modal-header{
  position: absolute;
  z-index: 1;
  background-color: #F3F6FF;
  top: 0;
  width: 100%;
}

.workspace-modal-body {
  padding-top: 45px;
}

p {
  font-weight: 500;
  margin: 0;
}

hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}

ul {
  text-decoration: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;

  li{
    list-style: none;
    hr{
      margin-bottom: 0;
    }
  }

  .workspace-item {
    padding: 0.5rem;
    gap: 0.625rem;
    display: flex;
    align-items: center;
    border-radius: 10px;
    background-color: #FFFFFF;

    &:hover {
      background-color: #C6D2FD;
      color: white;
    }
  }
}

a {
  display: flex;
  text-decoration: none;
  color: #222;
  align-items: center;
  gap: 10px;

  img {
    width: 30px;
    height: 30px;
  }
}

.plus {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #93AAFD;
}
</style>