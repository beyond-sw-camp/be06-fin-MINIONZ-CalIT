<script setup>
import codeIcon from '@/assets/icon/chatIcon/code.svg';
import docsIcon from '@/assets/icon/chatIcon/docs.svg';
import imgIcon from '@/assets/icon/chatIcon/img.svg';
import pdfIcon from '@/assets/icon/chatIcon/pdf.svg';
import downloadIcon from '@/assets/icon/chatIcon/download.svg';

const files = [
  { name: 'i9.pdf', type: 'PDF', size: '9mb', icon: pdfIcon },
  { name: 'Screenshot-3817.png', type: 'PNG', size: '4mb', icon: imgIcon },
  { name: 'sharefile.docx', type: 'DOC', size: '555kb', icon: docsIcon },
  { name: 'Jerry-2020_I-9_Form.xxl', type: 'XXL', size: '24mb', icon: codeIcon},
];
const totalFiles = 125;
</script>

<template>
  <div class="file-list-container">
    <div class="file-header">
      <p>Files <span class="badge">{{ totalFiles }}</span></p>
    </div>
    <div class="file-list">
      <div class="file-item" v-for="(file, index) in files" :key="index">
        <div class="file-info">
          <div class="file-icon">
            <img :src="file.icon" alt="file icon"/>
          </div>
          <div class="file-details">
            <span class="file-name">{{ file.name }}</span>
            <span class="file-type">{{ file.type }} · {{ file.size }}</span>
          </div>
        </div>
        <button class="download-button">
          <img :src="downloadIcon" alt="download icon"/>
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.file-list-container{
  position: relative;
  height: 50vh;
  overflow: scroll;
}

.file-list {
  padding: 0 20px;
  height: 100%;
  overflow: auto;
  //margin-top: 70px;
  background-color: #fff;
}

.file-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  background-color: #FFF;
  position:  sticky;
  top: 0;
  height: 60px;
  //box-shadow: 3px 3px 3px 0 rgba(0, 0, 0, 0.15);
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  /* font-size: 20px; */
  font-weight: 500;
  border-top:  1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
  span{
    background-color: #EDF2F7;
    font-size: 12px;
    padding: 8px 12px ;
    border-radius: 24px;
    margin-left: 10px;
  }
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