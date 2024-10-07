<script setup>
  import { useRouter } from 'vue-router';
  import {useUserStore} from "@/stores/user/useUserStore";

  const router = useRouter();
  // const token = router.currentRoute.value.query.Authorization?.replace('Bearer%20', '');
  const token = decodeURIComponent(router.currentRoute.value.query.Authorization?.replace('Bearer%20', ''));

  if (token) {
    const userStore = useUserStore();
    userStore.setToken(token);
    router.push({ path: '/my/dashboard' });
  }

</script>

<template>
<div>
  social login success
</div>
</template>

<style scoped>

</style>