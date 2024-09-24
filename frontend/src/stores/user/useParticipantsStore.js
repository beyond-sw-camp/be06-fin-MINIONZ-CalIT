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
        console.log('Adding participant:', participant);
        if (!participants.value.some(p => p.id === participant.id)) {
            participants.value.push(participant);
            console.log('Updated participants:', participants.value);
        } else {
            console.log('Participant already exists:', participant);
        }
    };

    const removeParticipant = (id) => {
        participants.value = participants.value.filter(p => p.id !== id);
    };

    const saveParticipants = (saveLocation) => {
        console.log('Saving Location', saveLocation);
        console.log('Saving participants Top:', participants.value); // 여긴 값이 안옴
        if (!saveLocation.value) {
            saveLocation.value = ref([]);
            console.log('Creating new save location:', saveLocation.value);
        }
        console.log('Save location before assignment:', saveLocation.value);
        saveLocation.value = [...participants.value];
        console.log('Save location after assignment:', saveLocation.value);
        participants.value = [];
        participantsName.value = '';
        console.log('Saved participants Bottom:', participants.value, participantsName.value);
        console.log('Saved save location Bottom:', saveLocation.value);
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