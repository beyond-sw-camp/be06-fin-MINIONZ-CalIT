import { defineStore } from 'pinia';

export const useDocsEditStore = defineStore('document', {
    state: () => ({
        content: '',
    }),
    actions: {
        updateContent(newContent) {
            this.content = newContent;
        },
    },
});