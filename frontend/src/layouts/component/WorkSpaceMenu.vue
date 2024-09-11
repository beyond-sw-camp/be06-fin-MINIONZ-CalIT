<template>
  <ul>
    <li>
      <router-link to="/workspace/dashboard">
        <i class="icon-dashboard"/>
        <p class="outfit">Dashboard</p>
      </router-link>
    </li>
    <li>
      <router-link to="/workspace/schedule/monthly">
        <i class="icon-schedule"/>
        <p class="outfit">Schedule</p>
      </router-link>
    </li>
    <li>
      <router-link to="/workspace/board/qa">
        <i class="icon-board"/>
        <p class="outfit">Board</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link to="/workspace/board/qa">
            <i class="icon-qa"/>
            <p class="outfit">QA Board</p>
          </router-link>
        </li>
        <li>
          <router-link to="/workspace/board/error">
            <i class="icon-errors"/>
            <p class="outfit">Errors</p>
          </router-link>
        </li>
      </ul>
    </li>
    <hr>
    <li>
      <router-link to="/workspace/scrum/task/kanban">
        <i class="icon-scrum"/>
        <p class="outfit">Scrum</p>
      </router-link>
      <ul class="submenu">
        <li>
          <router-link to="/workspace/scrum/sprint">
            <div>
              <i class="icon-sprint"/>
            </div>
            <p class="outfit">Sprint</p>
          </router-link>
        </li>
        <li>
          <router-link to="/workspace/scrum/task/kanban">
            <i class="icon-task"/>
            <p class="outfit">Task</p>
          </router-link>
        </li>
        <li>
          <router-link to="/workspace/scrum/meeting">
            <i class="icon-meeting"/>
            <p class="outfit">Meeting</p>
          </router-link>
        </li>
      </ul>
    </li>
  </ul>
</template>

<style scoped>
ul{
  list-style: none;
  padding: 0;
  margin: 0;
  a{
    padding: 0 1rem;
    gap: 0.625rem;
    text-decoration: none;
    display: flex;
    color: #000;
    font-weight: 300;
    height: 2.5rem;
    align-items: center;
    &:hover {
      background-color: #C6D2FD;
      color: white;
      border-radius: 10px;
      p{
        font-weight: 500;
      }
    }
  }
}
p{
  margin: 0;
  font-size: 1rem;
}
.submenu{
  padding-left: 1.25rem;
}
hr{
  border: 1px solid #dfe5f1;
  width: 100%;
}

i{
  width: 24px;
  display: block;
  height: 24px;
}

.icon-dashboard{
  background-image: url("@/assets/icon/menu/dashboard.svg");
}
.icon-schedule{
  background-image: url("@/assets/icon/menu/schedule.svg");
}
.icon-board{
  background-image: url("@/assets/icon/menu/board.svg");
}
.icon-qa{
  background-image: url("@/assets/icon/menu/qa.svg");
}
.icon-errors{
  background-image: url("@/assets/icon/menu/errors.svg");
}
.icon-scrum{
  background-image: url("@/assets/icon/menu/scrum.svg");
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
</style>