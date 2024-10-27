<script setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user/useUserStore';

const router = useRouter();
const route = router.currentRoute.value;
const token = route.query.Authorization;

if (token) {
  const userStore = useUserStore();
  userStore.setToken(token);
  router.push({ path: '/user/social-redirection' });
} else {
  console.error('Authorization token not found in URL');
}
</script>

<template>
  <div class="test">
    social login success
  </div>
</template>

<style scoped>
.test {
  background-color: #f0f0f0;
  height: 100%;
  width: 100%;
}
</style>