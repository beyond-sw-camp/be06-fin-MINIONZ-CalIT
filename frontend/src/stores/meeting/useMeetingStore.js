import { meetingData } from '@/static/meetingData';

export const useMeetingStore = () => {
    const state = {
        meetings: meetingData,
    };

    const actions = {
        addMeeting(meeting) {
            state.meetings.push(meeting);
        },
        clearMeetings() {
            state.meetings = [];
        },
    };

    return {
        state,
        actions,
    };
}
