<script setup>
import { provide, ref, watch } from 'vue';
import { useRoute } from 'vue-router';

const contentsTitle = ref('Contents Area');
const contentsDescription = ref('Contents Description');
const showContentsTitle = ref(true);

provide('contentsTitle', contentsTitle);
provide('contentsDescription', contentsDescription);

const route = useRoute();

watch(
    () => route.path,
    (newPath) => {
      showContentsTitle.value = !newPath.includes('/chat');
    },
    { immediate: true }
);
</script>

<template>
  <div id="contents">
    <div v-if="showContentsTitle" class="contents-title">
      <h1>{{ contentsTitle }}</h1>
      <p>{{ contentsDescription }}</p>
    </div>
    <router-view />
  </div>
</template>

<style scoped>
#contents {
  height: calc(100vh - 60px);
  width: 100%;
  margin-top: 60px;
  overflow: hidden;
}
h1 {
  font-size: 24px;
  font-weight: 500;
  margin: 0;
}
p {
  font-size: 16px;
  margin: 0;
  color: #909090;
}
.contents-title {
  margin-top: 30px;
  padding: 0 30px;
}
</style>