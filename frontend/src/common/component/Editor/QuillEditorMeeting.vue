<script setup>
import { onMounted, ref } from 'vue';
import Quill from 'quill';
import 'quill/dist/quill.snow.css';
import { Stomp } from '@stomp/stompjs';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { useRoute } from 'vue-router';


const editor = ref(null);
let quillEditor = null;

let stompClient = null;
let timeoutId;
let editorChangeFromRemote = false;
const route = useRoute();
const meetingId =
  route.params.meetingId ||
  route.params.id ||
  route.params[route.params.length - 1];

// 세션 스토리지에서 사용자 정보 가져오기
function getUserFromSession() {
  const userSession = sessionStorage.getItem('userInfo');
  return userSession ? JSON.parse(userSession) : null;
}

// WebSocket 연결 설정
function connectWebSocket() {
  const user = getUserFromSession();
  if (!user || !user.loginId) {
    console.warn('사용자 정보가 세션 스토리지에 없습니다.');
    return;
  }

  const socket = new SockJS('/api/note');
  stompClient = Stomp.over(socket);
  stompClient.connect(
    { Authorization: `Bearer ${user.loginId}` }, // 여기를 수정했습니다
    (frame) => {
      console.log('Connected: ' + frame);
      // 노트 업데이트 구독
      stompClient.subscribe(`/topic/note/${meetingId}`, (noteMessage) => {
        showNoteUpdate(JSON.parse(noteMessage.body));
      });

  
    }
  );
}

// 노트 저장 요청
function saveNoteToDB() {
  axios
    .post('/api/note/save', null, { params: { meetingId } })
    .then((response) => {
      if (response.data.success) {
        alert(response.data.message || '회의록이 성공적으로 저장되었습니다.');
      } else {
        alert('회의록 저장에 실패했습니다. 코드: ' + response.data.code);
      }
    })
    .catch((error) => {
      console.error('회의록 저장에 실패했습니다:', error);
      alert('회의록 저장에 실패했습니다.');
    });
}

// 노트 수정 사항을 서버로 전송
function sendNoteUpdate() {
  if (!quillEditor) {
    console.warn('Quill editor is not initialized.');
    return;
  }

  let plainText = quillEditor.getText();
  plainText = plainText.replace(/\n/g, '');

  const user = getUserFromSession();
  if (!user) {
    console.warn('사용자 정보가 세션 스토리지에 없습니다.');
    return;
  }

  const noteMessage = {
    noteId: meetingId,
    noteContents: plainText,
    sender: user.userName,
  };

  stompClient.send(
    `/app/note/save/${meetingId}`, // 백틱 추가
    {},
    JSON.stringify(noteMessage)
  );
}


// 노트 업데이트 처리
function showNoteUpdate(noteMessage) {
  const user = getUserFromSession();
 

  if (
    user.userName !== noteMessage.sender &&
    noteMessage &&
    noteMessage.noteContents
  ) {
    const currentText = quillEditor.getText();
    if (currentText.trim() !== noteMessage.noteContents.trim()) {
      const delta = JSON.parse(noteMessage.noteContents);
      quillEditor.updateContents(delta);



    }
  } else {
    console.warn('No note contents to display.');
  }
}

// 백엔드에서 회의록 데이터를 불러오기
function loadNoteContent() {
  axios
    .get('/api/note/load', { params: { meetingId } })
    .then((response) => {
      const noteContent = response.data.result.noteContent;
      if (noteContent) {
        quillEditor.setText(noteContent);
      }
    })
    .catch((error) => {
      console.error('Failed to load note content:', error);
    });
}

onMounted(() => {


  if (editor.value) {
    quillEditor = new Quill(editor.value, {
      theme: 'snow',
      placeholder: '내용을 입력하세요...',
      modules: {
        toolbar: {
          container: [
            [{ header: [1, 2, false] }],
            ['bold', 'italic', 'underline'],
            ['image', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ direction: 'rtl' }],
            [{ color: [] }, { background: [] }],
            [{ align: [] }],
            ['clean'],
          ],
          handlers: {
            image: function () {
              const range = this.quill.getSelection();
              const input = document.createElement('input');
              input.setAttribute('type', 'file');
              input.setAttribute('accept', 'image/*');
              input.click();
              input.onchange = () => {
                const file = input.files[0];
                if (file) {
                  const reader = new FileReader();
                  reader.onload = (e) => {
                    const base64Image = e.target.result;
                    this.quill.insertEmbed(
                      range.index,
                      'image',
                      base64Image,
                      Quill.sources.USER
                    );
                  };
                  reader.readAsDataURL(file);
                }
              };
            },
          },
        },
   
      },
    });

   


    quillEditor.on('text-change', function (delta, oldDelta, source) {
      if (source === 'user' && !editorChangeFromRemote) {
        const user = getUserFromSession();

        const noteMessage = {
          noteId: meetingId,
          noteContents: JSON.stringify(delta),
          sender: user.userName,
        };

        stompClient.send(
          `/app/note/edit/${meetingId}`,
          {},
          JSON.stringify(noteMessage)
        );

        if (timeoutId) {
          clearTimeout(timeoutId);
        }

        timeoutId = setTimeout(() => {
          sendNoteUpdate();
        }, 1000);
      }
    });

    connectWebSocket();
    loadNoteContent();
  } else {
    console.warn('Editor ref is not initialized.');
  }
});
</script>

<template>
  <div class="editor-section">
    <span class="column">글 작성하기</span>
    <div ref="editor" class="content-editor" style="border: none"></div>
    <button @click="saveNoteToDB">회의록 저장하기</button>
  </div>
</template>

<style scoped>
.editor-section {
  margin-top: 30px;
  min-height: 50%;
}

.toolbar {
  border: none !important;
}

.ql-toolbar {
  border: none !important;
}

.ql-snow {
  border: none !important;
}

.editor-section div.ql-container.ql-snow {
  border: none !important;
}

.editor-section div.ql-toolbar.ql-snow {
  border: none !important;
  outline: none !important;
}
</style>
