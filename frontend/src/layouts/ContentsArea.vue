<script setup>
import { provide, ref } from 'vue';

const contentsTitle = ref('Contents Area');
const contentsDescription = ref('Contents Description');

provide('contentsTitle', contentsTitle);
provide('contentsDescription', contentsDescription);
</script>

<template>
  <div id="contents">
    <div class="contents-title">
      <h1>{{ contentsTitle }}</h1>
      <p>{{ contentsDescription }}</p>
    </div>
    <router-view />
  </div>
</template>

<style scoped>
#contents {
  height: 100%;
  width: 100%;
  box-shadow: inset 5px 5px 5px rgba(0, 0, 0, 0.08);
  margin-top: 60px;
}
h1 {
  font-size: 24px;
  font-weight: 500;
  margin: 0;
}
p{
  font-size: 16px;
  margin: 0;
  color: #909090;
}
.contents-title{
  margin-top: 30px;
  padding: 0 30px;
}
</style>