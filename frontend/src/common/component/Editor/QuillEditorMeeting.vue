<script setup>
// 실시간 통신용
import { onMounted, ref } from 'vue';
import Quill from 'quill';
import 'quill/dist/quill.bubble.css';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

// Vue refs for Quill editor and WebSocket client
const editor = ref(null);
let quillEditor = null; // Quill 인스턴스를 저장할 변수
let stompClient = null;

// Note information - should be dynamically set
const meetingId = 1; // Placeholder, should be set dynamically

// WebSocket 연결 설정
function connectWebSocket() {
  const socket = new SockJS('http://localhost:8080/note');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, (frame) => {
    console.log('Connected: ' + frame);

    // 노트 업데이트 구독
    stompClient.subscribe(`/topic/note`, (noteMessage) => {
      showNoteUpdate(JSON.parse(noteMessage.body));
    });
  });
}

// 노트 수정 사항을 서버로 전송
function sendNoteUpdate() {
  // Quill 인스턴스가 존재하지 않으면 함수 종료
  if (!quillEditor) {
    console.warn('Quill editor is not initialized.');
    return;
  }

  const delta = quillEditor.getContents();

  const noteMessage = {
    'noteContents': delta,
    'meetingId': meetingId // meetingId를 포함하여 전송
  };

  // 서버로 수정된 노트 전송 - 경로에 meetingId 포함
  stompClient.send(`/app/note/edit/${meetingId}`, {}, JSON.stringify(noteMessage));
}

// 다른 사용자가 보낸 노트 업데이트를 화면에 반영
function showNoteUpdate(noteMessage) {
  console.log(noteMessage);

  // Quill 에디터의 내용을 업데이트
  const currentRange = quillEditor.getSelection();
  quillEditor.setContents(noteMessage.noteContents);
  quillEditor.setSelection(currentRange.index, currentRange.length);
}

onMounted(() => {
  // Quill 에디터 초기화
  if (editor.value) {
    quillEditor = new Quill(editor.value, {
      theme: 'bubble',
      placeholder: '내용을 입력하세요...',
      modules: {
        toolbar: {
          container: [
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline'],
            ['image', 'code-block'],
            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
            [{ 'script': 'sub' }, { 'script': 'super' }],
            [{ 'indent': '-1' }, { 'indent': '+1' }],
            [{ 'direction': 'rtl' }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'align': [] }],
            ['clean']
          ],
          handlers: {
            'image': function () {
              const range = this.quill.getSelection();
              const value = prompt('Enter image URL');
              if (value) {
                this.quill.insertEmbed(range.index, 'image', value, Quill.sources.USER);
              }
            }
          }
        }
      }
    });

    // Quill 에디터에서 텍스트 변경 이벤트 리스너 추가
    let timeoutId;
    quillEditor.on('text-change', function (delta, oldDelta, source) {
      if (source === 'user') {
        // 기존 타이머가 있으면 지우기
        if (timeoutId) {
          clearTimeout(timeoutId);
        }

        // 1초 후에만 서버로 전송
        timeoutId = setTimeout(() => {
          sendNoteUpdate();
        }, 1000); // 1초 지연
      }
    });

    // WebSocket 연결 설정
    connectWebSocket();
  } else {
    console.warn('Editor ref is not initialized.');
  }
});
</script>
<template>
  <!-- Quill 에디터 섹션 -->
  <div class="editor-section">
        <span class="column">
          <i class="quill-editings column-icon"></i>
          글 작성하기
        </span>
    <div ref="editor" class="content-editor"></div>
  </div>
</template>

<style scoped>
.editor-section {
  margin-top: 30px;
}

.quill-editings {
  background-image: url("@/assets/icon/boardIcon/quillEditor.svg");
}


.ql-editor {
  min-height: 200px;
  font-size: 1rem;
  line-height: 1.5;
  color: #28303F;
}

.ql-bubble{
  //max-width: max-content;
}

.ql-tooltip{
  position: static;
}

.ql-bubble .ql-tooltip-arrow{
  display: none;
}

.ql-container {
  position: relative;
  overflow: visible;
}

.ql-bubble .ql-tooltip{
  position: static !important;
  max-width: fit-content !important;
}

.ql-bubble .ql-tooltip-arrow{
  display: none;
}
</style>