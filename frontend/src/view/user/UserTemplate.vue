<script setup>

</script>

<template>
  <div class="user-page">
    <div class="user-template">
      <div class="user-content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<style scoped>
.user-page{
  background-color: #F5F6FA;
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
}
.user-template{
  background-color: #fff;
  width: 540px;
  border-radius: 20px;
  padding: 30px;
  //height: 450px;
}
.user-content{
  width: 100%;
}
</style>