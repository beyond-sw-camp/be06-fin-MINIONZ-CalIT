<script setup>
import { useRoute } from 'vue-router';
import BasicLayout from "@/layouts/BasicLayout.vue";
import Thumbnail from "@/view/thumbnail/ThumbnailPage.vue";
import { computed } from "vue";

const route = useRoute();

const showBasicLayout = computed(() => {
  return !(route.path.startsWith('/user') || route.path === '/' || route.path.startsWith('/social'));
});

const showThumbnail = computed(() => {
  return route.path === '/';
});
</script>

<template>
  <div id="AppVue">
    <template v-if="showBasicLayout">
      <BasicLayout>
        <router-view />
      </BasicLayout>
    </template>
    <template v-else-if="showThumbnail">
      <Thumbnail />
    </template>
    <template v-else>
      <router-view />
    </template>
  </div>
</template>

<style>
#AppVue {
  display: flex;
  justify-content: center;
  align-items: center;
  //height: 100vh;
  background-color: #f0f0f0;
  height: 100vh;
  width: 100vw;
}
</style>