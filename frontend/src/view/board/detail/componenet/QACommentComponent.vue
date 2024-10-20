<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { axiosInstance } from '@/utils/axiosInstance';

// 상태 변수들 정의
const newCommentTitle = ref('');  
const newCommentContent = ref('');  
const progressStatus = ref('NOT_STARTED');  
const newAttachment = ref(null);  
const comments = ref([]);  

const router = useRouter();
const qaBoardId = router.currentRoute.value.params.boardId;  


const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};


const fetchComments = async () => {
  try {
    const response = await axiosInstance.get(`/api/qacomment/search`, {
      params: { qaBoardId },
    });
    comments.value = response.data.result;  // 서버에서 반환된 댓글 목록 저장
  } catch (error) {
    console.error('댓글 목록 불러오기 중 오류가 발생했습니다:', error);
  }
};


const publishComment = async () => {
  try {
    const formData = new FormData();

    // JSON 데이터를 문자열로 변환하여 FormData에 추가 (request)
    formData.append(
      'request',
      JSON.stringify({
        qaCommentTitle: newCommentTitle.value,  // 댓글 제목
        qaCommentContent: newCommentContent.value,  // 댓글 내용
        progressStatus: progressStatus.value,  // 진행 상태
      })
    );

    // 파일이 있을 때만 파일을 FormData에 추가 (files)
    if (newAttachment.value) {
      formData.append('files', newAttachment.value);  // 서버에서 받는 MultipartFile[] 배열
    }

    // POST 요청 전송
    const response = await axiosInstance.post(`/api/qacomment/write?qaBoardId=${qaBoardId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    console.log('댓글 등록 성공:', response.data);

    // 댓글 등록 후 목록 갱신
    fetchComments();

    // 상태 초기화
    newCommentTitle.value = '';
    newCommentContent.value = '';
    progressStatus.value = 'NOT_STARTED';
    newAttachment.value = null;

  } catch (error) {
    console.error('댓글 등록 중 오류가 발생했습니다:', error);
  }
};

// 파일 선택 핸들러
const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    newAttachment.value = file;
  }
};

// 페이지가 로드될 때 댓글 목록 불러오기
onMounted(fetchComments);
</script>

<template>
  <div class="comment-section">
    <div class="comment-input">
      <div class="input-wrap">
        <!-- 댓글 제목 입력창 -->
        <input v-model="newCommentTitle" placeholder="댓글 제목을 입력하세요" class="comment-title" />

        <!-- 댓글 내용 입력창 -->
        <textarea v-model="newCommentContent" placeholder="댓글 내용을 입력하세요" rows="2"></textarea>

        <!-- 진행 상태 드롭다운 -->
        <label for="progress-status">진행 상태</label>
        <select v-model="progressStatus" class="progress-status" id="progress-status">
          <option value="NOT_STARTED">Not Started</option>
          <option value="IN_PROGRESS">In Progress</option>
          <option value="DONE">Done</option>
        </select>

        <div class="input-footer">
          <!-- 파일 첨부 버튼 -->
          <button class="attachment-button">
            <label class="file-input-icon" for="file"></label>
            <input type="file" id="file" style="display: none" @change="handleFileUpload" />
          </button>

          <!-- 댓글 게시 버튼 -->
          <button class="publish-button" @click="publishComment">Publish</button>
        </div>
      </div>

      <!-- 파일 첨부 시 표시 -->
      <div v-if="newAttachment" class="comment-attachment">
        <i class="file-icon"></i>
        <span>{{ newAttachment.name }}</span>
      </div>
    </div>

    <!-- 댓글 목록 표시 -->
    <div class="comment-list" v-if="comments.length > 0">
      <div v-for="comment in comments" :key="comment.qaCommentId" class="comment-item">
        <div class="comment-header">
          <!-- 사용자 이름 및 작성 시간 표시 -->
          <div class="comment-info">
            <span class="user-name">{{ comment.userName }}</span>
            <span class="comment-time">{{ formatDate(comment.createdAt) }}</span>
          </div>
        </div>

        <!-- 댓글 제목과 진행 상태 표시 -->
        <div class="comment-title-status">
          <strong>제목:</strong> {{ comment.qaCommentTitle }}<br/>
          <strong>진행 상태:</strong> {{ comment.progressStatus }}
        </div>

        <!-- 댓글 내용 표시 -->
        <div class="comment-body">
          <p>{{ comment.qaCommentContent }}</p>

          <!-- 첨부된 이미지가 있는 경우 표시 -->
          <div v-if="comment.images && comment.images.length > 0" class="comment-images">
            <div v-for="image in comment.images" :key="image.qaCommentImageId">
              <img :src="image.imageUrl" alt="Comment Image" class="comment-image" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 스타일 정의 */
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

.comment-title {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  width: 100%;
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
}

.progress-status {
  margin-top: 10px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  width: 100%;
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

.comment-attachment {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.comment-list {
  margin-top: 20px;
}

.comment-item {
  padding: 10px;
  border-bottom: 1px solid #d7d7d7;
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.comment-info {
  display: flex;
  flex-direction: column;
}

.user-name {
  font-weight: bold;
}

.comment-time {
  color: gray;
  font-size: 12px;
}

.comment-title-status {
  margin: 10px 0;
}

.comment-body {
  font-size: 14px;
}

.comment-images {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.comment-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
}
</style>
