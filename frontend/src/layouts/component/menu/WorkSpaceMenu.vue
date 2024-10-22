<script setup>
import { useRoute } from 'vue-router';
import { ref, watch } from "vue";

const route = useRoute();
const workspaceId = ref(route.params.workspaceId);

watch(() => route.params.workspaceId, (newId) => {
  workspaceId.value = newId;
});
</script>

<template>
  <ul>
    <li>
      <router-link :to="`/workspace/${workspaceId}/dashboard`">
        <i class="icon-dashboard"/>
        <p class="outfit">Dashboard</p>
      </router-link>
    </li>
    <li>
      <router-link :to="`/workspace/${workspaceId}/schedule/monthly`">
        <i class="icon-schedule"/>
        <p class="outfit">Schedule</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link :to="`/workspace/${workspaceId}/schedule/monthly`">
            <i class="icon-monthly"/>
            <p class="outfit">Monthly</p>
          </router-link>
        </li>
        <li>
          <router-link :to="`/workspace/${workspaceId}/schedule/weekly`">
            <i class="icon-weekly"/>
            <p class="outfit">Weekly</p>
          </router-link>
        </li>
      </ul>
    </li>
    <li>
      <router-link :to="`/workspace/${workspaceId}/chat`">
        <i class="icon-chat"/>
        <p class="outfit">Chat</p>
      </router-link>
    </li>
    <hr>
    <li>
      <router-link :to="`/workspace/${workspaceId}/scrum/task/kanban`">
        <i class="icon-scrum"/>
        <p class="outfit">Scrum</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link :to="`/workspace/${workspaceId}/scrum/sprint/list`">
            <div>
              <i class="icon-sprint"/>
            </div>
            <p class="outfit">Sprint</p>
          </router-link>
        </li>
        <li>
          <router-link :to="`/workspace/${workspaceId}/scrum/task/kanban`">
            <i class="icon-task"/>
            <p class="outfit">Task</p>
          </router-link>
        </li>
        <li>
          <router-link :to="`/workspace/${workspaceId}/scrum/issue/list`">
            <div>
              <i class="icon-sprint"/>
            </div>
            <p class="outfit">Issue</p>
          </router-link>
        </li>
        <li>
          <router-link :to="`/workspace/${workspaceId}/scrum/meeting/list`">
            <i class="icon-meeting"/>
            <p class="outfit">Meeting</p>
          </router-link>
        </li>
      </ul>
    </li>
    <hr>
    <li>
      <router-link :to="`/workspace/${workspaceId}/scrum/board/qa/list`">
        <i class="icon-board"/>
        <p class="outfit">Board</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link :to="`/workspace/${workspaceId}/scrum/board/qa/list`">
            <i class="icon-qa"/>
            <p class="outfit">QA Board</p>
          </router-link>
        </li>
        <li>
          <router-link :to="`/workspace/${workspaceId}/scrum/board/error/list`">
            <i class="icon-errors"/>
            <p class="outfit">Errors</p>
          </router-link>
        </li>
      </ul>
    </li>

  </ul>
</template>

<style scoped>
ul {
  list-style: none;
  padding: 0;
  margin: 0;
}
a {
  padding: 0 1rem;
  gap: 0.625rem;
  text-decoration: none;
  display: flex;
  color: #000;
  font-weight: 300;
  height: 2.5rem;
  align-items: center;
}
a:hover {
  background-color: #C6D2FD;
  color: white;
  border-radius: 10px;
}
a:hover p {
  font-weight: 500;
}
p {
  margin: 0;
  font-size: 1rem;
}
.submenu {
  padding-left: 1.25rem;
}
hr {
  border: 1px solid #dfe5f1;
  width: 100%;
  margin: 10px 0;
}
i {
  width: 24px;
  display: block;
  height: 24px;
}
.icon-dashboard {
  background-image: url("@/assets/icon/menu/dashboard.svg");
}
.icon-schedule {
  background-image: url("@/assets/icon/menu/schedule.svg");
}
.icon-monthly {
  background-image: url("@/assets/icon/menu/monthly.svg");
}
.icon-weekly {
  background-image: url("@/assets/icon/menu/weekly.svg");
}
.icon-board {
  background-image: url("@/assets/icon/menu/board.svg");
}
.icon-qa {
  background-image: url("@/assets/icon/menu/qa.svg");
}
.icon-errors {
  background-image: url("@/assets/icon/menu/errors.svg");
}
.icon-scrum {
  background-image: url("@/assets/icon/menu/scrum.svg");
}
.icon-sprint {
  background-image: url("@/assets/icon/menu/sprint.svg");
}
.icon-task {
  background-image: url("@/assets/icon/menu/task.svg");
}
.icon-meeting {
  background-image: url("@/assets/icon/menu/meeting.svg");
}
.icon-chat {
  background-image: url("@/assets/icon/menu/chat.svg");
}
</style>