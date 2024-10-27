<script setup>
import TaskViewBar from "@/view/scrum/Task/component/TaskViewBar.vue";
import {useRoute} from "vue-router";

const route = useRoute();
</script>

<template>
  <div>
    <TaskViewBar v-if="!route.path.endsWith('create')"/>
    <router-view />
  </div>

</template>

<style scoped>

</style>