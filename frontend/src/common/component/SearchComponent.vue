<script setup>
import { defineProps, defineEmits, onMounted, ref } from 'vue';
import Multiselect from 'vue-multiselect';
import 'vue-multiselect/dist/vue-multiselect.min.css';

const selectedLanguage = ref('');

defineProps({
  link: String,
});

defineEmits(['update:selectedLanguage']);

const isErrorPage = ref(false);

onMounted(async () => {
  if (window.location.href.endsWith('/error/list')) {
    isErrorPage.value = true;
  }
});
</script>

<template>
  <div class="toolbar">
    <div class="filter-search">
      <div v-if="isErrorPage" class="filter">
        <multiselect v-model="selectedLanguage" :options="['JAVA', 'C', 'PYTHON', 'JS', 'SQL']" @input="$emit('update:selectedLanguage', selectedLanguage)" />
      </div>
      <div class="search">
        <input type="text" class="search-input" placeholder="Search..." />
        <span class="search-icon">🔍</span>
      </div>
    </div>

    <!-- Create 버튼 -->
    <router-link :to="link" class="create-button">
      <span class="create-icon">+</span> Create
    </router-link>
  </div>
</template>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
}

.filter-search {
  display: flex;
  align-items: center;
}

.filter-button {
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 6px;
  background-color: #f8f8f8;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.dropdown-icon {
  margin-left: 5px;
}

.search {
  display: flex;
  align-items: center;
  position: relative;
}

.search-input {
  padding: 8px 8px 8px 30px;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  width: 200px;
  background-color: #f8f8f8;
}

.search-icon {
  position: absolute;
  margin-left: 10px;
}

.create-button {
  text-decoration: none;
  background-color: #e0e8ff;
  color: #666daf;
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  align-items: center;
  font-weight: bold;
}

.create-icon {
  margin-right: 5px;
}
</style>