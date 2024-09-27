import { issueData } from "@/static/issueData";
import { ref } from "vue";
import { defineStore } from 'pinia';

export const useIssueStore = defineStore('issueStore', () => {
    const issues = ref(issueData || []);

    const getIssues = () => {
        return issues.value;
    };

    const addIssue = (issue) => {
        issues.value.push(issue);
    };

    // const updateIssue = (index, updatedIssue) => {
    //     if (index >= 0 && index < issues.value.length) {
    //         issues.value[index] = updatedIssue;
    //     }
    // };
    //
    // const deleteIssue = (index) => {
    //     if (index >= 0 && index < issues.value.length) {
    //         issues.value.splice(index, 1);
    //     }
    // };

    return {
        getIssues,
        addIssue,
        // updateIssue,
        // deleteIssue
    };
});