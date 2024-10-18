<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  comments: {
    type: Array,
    default: () => []
  }
});

const emit = defineEmits(['update-comments']);

const newComment = ref('');
const newAttachment = ref(null);

const publishComment = () => {
  if (newComment.value.trim() !== '') {
    const newComments = [...props.comments, {
      id: props.comments.length + 1,
      user: {name: 'New User', avatar: ''},
      text: newComment.value,
      timeAgo: 'just now',
      attachment: newAttachment.value
    }];
    emit('update-comments', newComments);
    newAttachment.value = null;
  }
};

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    newAttachment.value = file.name;
  }
};

const adjustTextareaHeight = (event) => {
  const textarea = event.target;
  textarea.style.height = 'auto';
  textarea.style.height = `${textarea.scrollHeight}px`;
};
</script>

<template>
  <div class="comment-section">
    <div class="comment-input">
      <div class="input-wrap">
        <textarea v-model="newComment" placeholder="답글을 작성하세요" rows="2" @input="adjustTextareaHeight"></textarea>
        <div class="input-footer">
          <button class="attachment-button">
            <label class="file-input-icon" for="file"></label>
            <input type="file" id="file" style="display: none" @change="handleFileUpload"/>
          </button>
          <button class="publish-button" @click="publishComment">Publish</button>
        </div>
      </div>
      <div v-if="newAttachment" class="comment-attachment">
        <i class="file-icon"></i>
        <span>{{ newAttachment }}</span>
      </div>
    </div>

    <div class="comment-list">
      <div v-for="comment in comments" :key="comment.id" class="comment-item">
        <div class="comment-header">
          <img :src="comment.user.avatar" alt="avatar" class="avatar"/>
          <div class="comment-info">
            <span class="user-name">{{ comment.user.name }}</span>
            <span class="comment-time">{{ comment.timeAgo }}전 작성된 답글</span>
          </div>
          <i class="fas fa-ellipsis-h comment-options"></i>
        </div>
        <div class="comment-body">
          <p>{{ comment.text }}</p>
          <div v-if="comment.attachment" class="comment-attachment">
            <i class="file-icon"></i>
            <span>{{ comment.attachment }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.comment-section {
  margin-top: 30px;
}

.comment-input {
  margin-bottom: 20px;
  display: flex;
  border: 1px solid #d7d7d7;
  border-radius: 10px;
  padding: 10px;
  flex-direction: column;
}

.input-wrap {
  width: 100%;
  display: flex;
  align-items: flex-start;
}

textarea {
  width: calc(100% - 90px);
  padding: 10px;
  border-radius: 5px;
  font-size: 14px;
  resize: none;
  outline: none;
  border: none;
  flex-grow: 1;
  overflow: hidden;
}

.input-footer {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
  align-items: center;
}

.attachment-button {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  margin-right: 10px;
}

.file-input-icon {
  background-image: url("@/assets/icon/chatIcon/clip.svg");
  display: inline-block;
  height: 30px;
  width: 30px;
  background-size: cover;
  cursor: pointer;
}

.publish-button {
  background-color: #666daf;
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  height: 40px;
}

.publish-button:disabled {
  background-color: #ccc;
}

.comment-list {
  margin-top: 20px;
}

.comment-item {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 10px;
  margin-bottom: 15px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.avatar {
  width: 36px;
  height: 36px;
  margin-right: 15px;
}

.comment-info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.user-name {
  font-weight: 500;
  font-size: 16px;
}

.comment-time {
  color: #777;
  font-size: 12px;
}

.comment-options {
  margin-left: auto;
  font-size: 16px;
  cursor: pointer;
}

.comment-body {
  font-size: 14px;
  color: #333;
}

.comment-attachment {
  display: flex;
  align-items: center;
  margin-top: 10px;
  border: 1px solid #d7d7d7;
  border-radius: 14px;
  width: fit-content;
  padding: 5px 10px;
  background-color: rgba(255, 255, 255, 0.5);
}

.comment-attachment i {
  color: red;
  margin-right: 5px;
  background-image: url("@/assets/icon/boardIcon/pdf.svg");
  display: inline-block;
  height: 20px;
  width: 20px;
}

.comment-attachment span {
  font-size: 12px;
  color: #555;
}
</style>