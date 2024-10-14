<script setup>
// 실시간 통신용
import {onMounted} from 'vue';
import Quill from 'quill';
import 'quill/dist/quill.snow.css';
import {Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {useUserStore} from "@/stores/user/useUserStore";

// Vue refs for Quill editor and WebSocket client
let quillEditor = null; // Quill 인스턴스를 저장할 변수
let stompClient = null;
let editorChangeFromRemote = false;
const userStore = useUserStore();
const toolbar = [
    [{'size': []}],
    ['bold', 'italic', 'underline', 'strike'],
    [{'color': []}, {'background': []}],
    [{'script': 'super'}, {'script': 'sub'}],
    [{'header': '1'}, {'header': '2'}, 'blockquote', 'code-block'],
    [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
    ['direction', {'align': []}],
    ['link', 'image', 'video', 'formula'],
    ['clean']
]

// Note information - should be dynamically set
const meetingId = 1; // Placeholder, should be set dynamically

// WebSocket 연결 설정
function connectWebSocket() {
  const socket = new SockJS('/api/note');
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
    noteContents: delta,
    meetingId: meetingId, // meetingId를 포함하여 전송
  };

  // 서버로 수정된 노트 전송 - 경로에 meetingId 포함
  stompClient.send(
    `/app/note/edit/${meetingId}`,
    {},
    JSON.stringify(noteMessage)
  );
}

onMounted(() => {
  // Quill 에디터 초기화
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
        },
      },

    quillEditor.on('text-change', function (delta, oldDelta, source) {
        // 사용자로부터 발생한 변경사항만 전송(전송 받은 데이터로 업데이트 되는 것은 보내지 않음)
        if (source === 'user' && !editorChangeFromRemote) {
            const noteMessage = {
                noteContents: delta,
                meetingId: meetingId,
                senderLoginId: userStore.user.value.loginId
            };
            stompClient.send(`/app/note/edit/${meetingId}`, {}, JSON.stringify(noteMessage));
        }
    });

    // WebSocket 연결 설정
    connectWebSocket();

});
</script>
<template>
  <!-- Quill 에디터 섹션 -->

  <div class="editor-section">
    <span class="column">
      <!--          <i class="quill-editings column-icon"></i>-->
      글 작성하기
    </span>
    <div ref="editor" class="content-editor" style="border: none"></div>
  </div>

    <div id="editor-container"></div>
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
