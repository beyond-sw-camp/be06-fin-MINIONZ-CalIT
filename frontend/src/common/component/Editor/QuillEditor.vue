<script setup>
import {onMounted, ref} from "vue";
import Quill from "quill";
import 'quill/dist/quill.bubble.css';

const editor = ref(null);

onMounted(() => {
  if (editor.value) {
    new Quill(editor.value, {
      theme: 'bubble',
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
            'image': function() {
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
</style>