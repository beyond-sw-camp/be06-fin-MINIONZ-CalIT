<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import pdfIcon from '@/assets/icon/chatIcon/pdf.svg';
import imgIcon from '@/assets/icon/chatIcon/img.svg';
import docsIcon from '@/assets/icon/chatIcon/docs.svg';
import codeIcon from '@/assets/icon/chatIcon/code.svg';
import downloadIcon from '@/assets/icon/chatIcon/download.svg';
import { useChatMessageStore } from '@/stores/chat/useChatMessageStore';

const route = useRoute();
const chatroomId = route.params.chatroomId;
const chatMessageStore = useChatMessageStore();
const files = ref([]);

// 파일 크기 변환 함수
const formatFileSize = (size) => {
  if (size === 0) return '0 Bytes';
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
  const i = Math.floor(Math.log(size) / Math.log(k));
  return parseFloat((size / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

// 파일 목록 가져오기
const fetchFiles = async () => {
  if(!chatroomId){
    return;
  }
  const fileMessages = await chatMessageStore.fetchFileMessages(chatroomId);
  if (fileMessages) {
    files.value = fileMessages;
  }
};

onMounted(fetchFiles);

const getFileIcon = (fileType) => {
  switch (fileType) {
    case 'application/pdf':
      return pdfIcon;
    case 'image/png':
    case 'image/jpeg':
    case 'image/jpg':
      return imgIcon;
    case 'application.document':
      return docsIcon;
    case 'code':
      return codeIcon;
    default:
      return pdfIcon;
  }
};
</script>

<template>
  <div class="file-list-container">
    <div class="file-header">
      <p>
        Files <span class="badge">{{ files.length }}</span>
      </p>
    </div>
    <div class="file-list">
      <div class="file-item" v-for="(file, index) in files" :key="index">
        <div class="file-info">
          <div class="file-icon">
            <img :src="getFileIcon(file.fileType)" alt="file icon" />
          </div>
          <div class="file-details">
            <span class="file-name">{{ file.fileName }}</span>
            <span class="file-type"
              >{{ file.fileType }} · {{ formatFileSize(file.fileSize) }}</span
            >
          </div>
        </div>
        <a :href="file.fileUrl" target="_blank" class="download-button">
          <img :src="downloadIcon" alt="download icon" />
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped>
.file-list-container {
  position: relative;
  height: 50vh;
  overflow: scroll;
}

.file-list {
  padding: 0 20px;
  height: 100%;
  overflow: auto;
  background-color: #fff;
}

.file-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  background-color: #fff;
  position: sticky;
  top: 0;
  height: 60px;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  font-weight: 500;
  border-top: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
}

.file-header span {
  background-color: #edf2f7;
  font-size: 12px;
  padding: 8px 12px;
  border-radius: 24px;
  margin-left: 10px;
}

.file-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #f0f0f0;
}

.file-info {
  display: flex;
  align-items: center;
}

.file-icon {
  width: 40px;
  height: 40px;
  margin-right: 15px;
}

.file-details {
  display: flex;
  flex-direction: column;
}

.file-name {
  font-weight: 400;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 150px;
}

.file-type {
  color: #888;
  font-size: 12px;
}

.download-button {
  background: none;
  border: none;
  cursor: pointer;
}
</style>
