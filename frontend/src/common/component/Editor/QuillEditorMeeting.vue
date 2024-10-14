<script setup>
// 실시간 통신용
import {onMounted} from 'vue';
import Quill from 'quill';
import 'quill/dist/quill.snow.css';
import {Stomp} from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {useUserStore} from "@/stores/user/useUserStore";
import {useRoute} from 'vue-router'; // URL 정보를 가져오기 위한 Vue Router

// Vue refs for Quill editor and WebSocket client
let quillEditor = null; // Quill 인스턴스를 저장할 변수
let stompClient = null;
let editorChangeFromRemote = false;
const userStore = useUserStore();
const route = useRoute(); // 현재 라우트를 가져옴
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

// URL에서 meetingId 가져오기
const meetingId = route.params.meetingId || route.params.id || route.params[route.params.length - 1];  // URL 마지막 값을 meetingId로 사용

// WebSocket 연결 설정
function connectWebSocket() {
    const socket = new SockJS('http://localhost:8080/note');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);

        // 노트 업데이트 구독
        stompClient.subscribe(`/topic/note`, (noteMessage) => {
            // 보낸 사람과 받는 사람의 loginId가 같으면(본인이 보낸거면) 업데이트 하지 않음
            if (userStore.user.value.loginId !== JSON.parse(noteMessage.body).senderLoginId) {
                const delta = JSON.parse(noteMessage.body).noteContents;
                editorChangeFromRemote = true;
                quillEditor.updateContents(delta);  // 외부로부터 온 Delta 변경사항 적용
                editorChangeFromRemote = false;
            }

        });
    });
}

onMounted(() => {
    // Quill 에디터 초기화
    quillEditor = new Quill("#editor-container", {
        modules: {'toolbar': toolbar},
        theme: "snow"
    });

    quillEditor.on('text-change', function (delta, oldDelta, source) {
        // 사용자로부터 발생한 변경사항만 전송(전송 받은 데이터로 업데이트 되는 것은 보내지 않음)
        if (source === 'user' && !editorChangeFromRemote) {
            const noteMessage = {
                noteContents: delta,
                meetingId: meetingId, // URL에서 가져온 meetingId 사용
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