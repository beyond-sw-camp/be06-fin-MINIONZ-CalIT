<script setup>

</script>

<template>
  <div class="contents-container">
    <slot></slot>
  </div>
</template>

<style scoped>
.contents-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  background-color: #FFFFFF;
  margin-left: 16.25rem;
  overflow: hidden;
}
</style>