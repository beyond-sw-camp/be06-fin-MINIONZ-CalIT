import { ref } from 'vue';
import axios from 'axios';
import { defineStore} from "pinia";
// import { taskData } from '@/static/taskData';
import { getTaskCountBackgroundColor, getTaskCountColor } from '@/utils/taskUtils';

//axios ver
export const useTaskStore = defineStore({
    const taskDatas = ref([]);
    const addTask = async (task) => {
        const response = await axios.post('api/task', task);
        taskDatas.value.push(response.data);
    };
    const getTask = async (index) => {
        const response = await axios.get(`http://localhost:8080/task/${index}`);
        return response.data;
    };
    const getTasks = async () => {
        const response = await axios.get('http://localhost:8080/task');
        taskDatas.value = response.data;
    };
    const getTaskCount = () => {
        return taskDatas.value.length
    }
})


// export const useTaskStore = () => {
//     const taskDatas = ref(taskData || []);
//
//     const addTask = (task) => {
//         taskDatas.value.push(task);
//     };
//
//     const getTask = (index) => {
//         return taskDatas.value[index];
//     };
//
//     const getTasks = () => {
//         return taskDatas.value;
//     };
//
//     const getTaskCount = () => {
//         return taskDatas.value.length
//     }
//
//     const getTaskTitle = () => {
//         return taskDatas.value.flatMap(status => status.tasks.map(task => ({ title: task.title, status: status.status })));
//     }
//
//     const updateTask = (index, updatedTask) => {
//         if (index >= 0 && index < taskDatas.value.length) {
//             taskDatas.value[index] = updatedTask;
//         }
//     };
//
//     const updateTaskStatus = (index, status) => {
//         if (index >= 0 && index < taskDatas.value.length) {
//             taskDatas.value[index].status = status;
//             taskDatas.value[index].backgroundColor = getTaskCountBackgroundColor(status);
//             taskDatas.value[index].color = getTaskCountColor(status);
//         }
//     };
//     const deleteTask = (index) => {
//         if (index >= 0 && index < taskDatas.value.length) {
//             taskDatas.value.splice(index, 1);
//         }
//     };
//
//     return {
//         addTask,
//         getTask,
//         getTasks,
//         getTaskCount,
//         getTaskTitle,
//         updateTask,
//         updateTaskStatus,
//         deleteTask
//     };
// }