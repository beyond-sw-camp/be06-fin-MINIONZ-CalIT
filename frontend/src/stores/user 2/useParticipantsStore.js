import { ref, computed } from 'vue';
import { userData } from "@/static/userData";

export function useParticipants() {
    const participantsName = ref('');
    const participants = ref([]);
    const userList = ref([]);

    const filteredUsers = computed(() => {
        return userData.filter(user => user.username.includes(participantsName.value));
    });

    const addParticipant = (participant) => {
        if (!participants.value.some(p => p.id === participant.id)) {
            participants.value.push(participant);
        }
    };

    const removeParticipant = (id) => {
        participants.value = participants.value.filter(p => p.id !== id);
    };

    const saveParticipants = (saveLocation) => {
        saveLocation.value = [...participants.value];
        participants.value = [];
        participantsName.value = '';
    };

    return {
        participantsName,
        participants,
        userList,
        filteredUsers,
        addParticipant,
        removeParticipant,
        saveParticipants
    };
}