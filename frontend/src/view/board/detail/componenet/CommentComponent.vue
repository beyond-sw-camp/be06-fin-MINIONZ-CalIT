<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { axiosInstance } from '@/utils/axiosInstance';

// 상태 변수들 정의
const newComment = ref(''); // 새 댓글 내용
const newAttachment = ref(null); // 첨부 파일
const comments = ref([]); // 댓글 목록

// useRouter로 현재 라우터 정보 가져오기
const router = useRouter();
const errorBoardId = router.currentRoute.value.params.boardId;

// 날짜를 yyyy-MM-dd 형식으로 변환하는 함수
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

// 댓글 목록 불러오기 로직
const fetchComments = async () => {
  try {
    const response = await axiosInstance.get(`/api/errcomment/search`, {
      params: { errorBoardId },
    });
    comments.value = response.data.result; // 서버에서 반환된 댓글 목록 저장
  } catch (error) {
    console.error('댓글 목록 불러오기 중 오류가 발생했습니다:', error);
  }
};

// 댓글 작성 로직
const publishComment = async () => {
  try {
    const formData = new FormData();

    // JSON 데이터를 문자열로 변환하여 FormData에 추가 (request)
    formData.append(
      'request',
      JSON.stringify({
        errCommentContent: newComment.value,
      })
    );

    // 파일이 있을 때만 파일을 FormData에 추가 (files)
    if (newAttachment.value) {
      formData.append('files', newAttachment.value);
    }

    await axiosInstance.post(
      `/api/errcomment/write?errorBoardId=${errorBoardId}`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );

    // 댓글 등록 후 목록 갱신
    fetchComments();

    // 상태 초기화
    newComment.value = '';
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
        <!-- 댓글 입력창 -->
        <textarea
          v-model="newComment"
          placeholder="답글을 작성하세요"
          rows="2"
        ></textarea>

        <div class="input-footer">
          <!-- 파일 첨부 버튼 -->
          <button class="attachment-button">
            <label class="file-input-icon" for="file"></label>
            <input
              type="file"
              id="file"
              style="display: none"
              @change="handleFileUpload"
            />
          </button>

          <!-- 댓글 게시 버튼 -->
          <button class="publish-button" @click="publishComment">
            Publish
          </button>
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
      <div
        v-for="comment in comments"
        :key="comment.errorCommentId"
        class="comment-item"
      >
        <div class="comment-header">
          <!-- 사용자 이름 및 작성 시간 표시 -->
          <div class="comment-info">
            <span class="user-name">{{ comment.userName }}</span>
            <span class="comment-time">{{
              formatDate(comment.createdAt)
            }}</span>
          </div>
        </div>

        <!-- 댓글 내용 표시 -->
        <div class="comment-body">
          <p>{{ comment.errorCommentContent }}</p>

          <!-- 첨부된 이미지가 있는 경우 표시 -->
          <div
            v-if="comment.images && comment.images.length > 0"
            class="comment-images"
          >
            <div
              v-for="image in comment.images"
              :key="image.errorCommentImageId"
            >
              <img
                :src="image.imageUrl"
                alt="Comment Image"
                class="comment-image"
              />
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
  background-image: url('@/assets/icon/chatIcon/clip.svg'); /* 아이콘 경로 */
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

.file-icon {
  background-image: url('@/assets/icon/boardIcon/pdf.svg'); /* 아이콘 경로 */
  display: inline-block;
  height: 20px;
  width: 20px;
  background-size: cover;
  margin-right: 5px;
}
</style>
