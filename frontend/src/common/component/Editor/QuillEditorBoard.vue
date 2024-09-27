<script setup>
// 일반 작성용
import {onMounted, ref} from "vue";
import Quill from "quill";
import 'quill/dist/quill.snow.css';

const editor = ref(null);

onMounted(() => {
  if (editor.value) {
    new Quill(editor.value, {
      theme: 'snow',
      placeholder: '내용을 입력하세요...',
      modules: {
        toolbar: {
          container: [
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline'],
            ['image', 'code-block'],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'script': 'sub'}, { 'script': 'super' }],
            [{ 'indent': '-1'}, { 'indent': '+1' }],
            [{ 'direction': 'rtl' }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'align': [] }],
            ['clean']
          ],
          handlers: {
            'image': function () {
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
                    this.quill.insertEmbed(range.index, 'image', base64Image, Quill.sources.USER);
                  };
                  reader.readAsDataURL(file);
                }
              };
            }
          }
        }
      }
    });
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

.ql-toolbar.ql-snow{
  border: none;
}

.ql-container.ql-snow {
  border: none;
}
</style>