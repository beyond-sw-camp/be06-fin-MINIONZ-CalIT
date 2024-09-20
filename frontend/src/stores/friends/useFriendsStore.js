// 친구 추가 (GET) /friend/add?id=test&name=테스트 | fetchFriends()
// {
// 		"code": "200",
// 		"isSuccess": true,
// 		"message": "친구 추가 성공",
// 		"result": {
// 		    }
// }
// 친구 목록 조회 (GET) /friend/list | fetchFriends()
// {
// 		"code": "200",
// 		"isSuccess": true,
// 		"message": "친구 목록 조회 성공",
// 		"result": {
// 					"id": "test",
// 					"name" : "테스트"
// 		    }
// }
// 친구 삭제 (DELETE) /friend/delete?id=test | deleteFriend()

import { ref } from 'vue';
import axios from 'axios';

export function useFriendsStore() {
    const friends = ref([]);
    const loading = ref(false);
    const error = ref(null);

    const fetchFriends = async () => {
        loading.value = true;
        error.value = null;
        try {
            const response = await axios.get('/friend/list');
            if (response.data.isSuccess) {
                friends.value = response.data.result;
            } else {
                error.value = response.data.message;
            }
        } catch (err) {
            error.value = err.message;
        } finally {
            loading.value = false;
        }
    };

    // const addFriend = async (id, name) => {
    //     loading.value = true;
    //     error.value = null;
    //     try {
    //         const response = await axios.get(`/friend/add?id=${id}&name=${name}`);
    //         if (response.data.isSuccess) {
    //             friends.value.push(response.data.result);
    //         } else {
    //             error.value = response.data.message;
    //         }
    //     } catch (err) {
    //         error.value = err.message;
    //     } finally {
    //         loading.value = false;
    //     }
    // };

    // const deleteFriend = async (id) => {
    //     loading.value = true;
    //     error.value = null;
    //     try {
    //         const response = await axios.delete(`/friend/delete?id=${id}`);
    //         if (response.data.isSuccess) {
    //             friends.value = friends.value.filter(friend => friend.id !== id);
    //         } else {
    //             error.value = response.data.message;
    //         }
    //     } catch (err) {
    //         error.value = err.message;
    //     } finally {
    //         loading.value = false;
    //     }
    // };

    return {
        friends,
        loading,
        error,
        fetchFriends,
        // addFriend,
        // deleteFriend,
    };
}