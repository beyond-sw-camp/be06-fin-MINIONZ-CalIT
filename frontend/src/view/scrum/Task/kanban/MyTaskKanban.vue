<script setup>
import { inject } from 'vue';
import TaskColumn from './component/KanbanColumn.vue';

import user3 from '@/assets/icon/persona/user3.svg';
import user2 from '@/assets/icon/persona/user2.svg';
import user1 from '@/assets/icon/persona/user1.svg';
import space1 from '@/assets/icon/persona/space1.svg';
import space2 from '@/assets/icon/persona/space2.svg';

const contentsTitle = inject('contentsTitle');
const contentsDescription = inject('contentsDescription');

contentsTitle.value = 'My Kanban';
contentsDescription.value = '나의 태스크를 살펴보세요!';

const tasks = [
  {
    id: 2,
    status: 'To Do',
    tasks: [
      { id: 3, title: 'Model Answer', labels: ['Backend'], avatars: [user3], taskNumber: "003", dueDate: '2024-10-01' },
      { id: 4, title: 'Add authentication pages', labels: ['Frontend', 'To Do'], avatars: [user2], taskNumber: "004", dueDate: '2024-10-01'}
    ]
  },
  {
    id: 3,
    status: 'In Progress',
    tasks: [
      {
        id: 5,
        title: 'Model Answer',
        labels: ['DB'],
        avatars: [user1, space1],
        taskNumber: "005",
        morePeople: 5,
        dueDate: '2024-10-01'
      }
    ]
  },
  {
    id: 4,
    status: 'Done',
    tasks: [
      {id: 6, title: 'Model Answer', labels: ['User'], avatars: [space2], taskNumber: "002", dueDate: '2024-10-01'}
    ]
  }
];
</script>

<template>
  <div class="kanban-board">
    <TaskColumn v-for="task in tasks" :key="task.id" :data="task"/>
  </div>
</template>

<style scoped>
.kanban-board {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
  overflow-x: auto;
}
</style>