package minionz.backend.scrum.meeting;

import lombok.RequiredArgsConstructor;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.meeting.model.request.CreateMeetingRequest;
import minionz.backend.scrum.meeting_participation.MeetingParticipationRepository;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingParticipationRepository meetingParticipationRepository;

    @Transactional
    public void createMeeting(User user, CreateMeetingRequest request, Long sprintId) {
        Meeting meeting = meetingRepository.save(
                Meeting
                        .builder()
                        .sprint(Sprint.builder().sprintId(sprintId).build())
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .meetingTitle(request.getTitle())
                        .meetingContents(request.getContents())
                        .build()
        );

        meetingParticipationRepository.save(MeetingParticipation.builder().meeting(meeting).user(user).build());
        request.getParticipants().forEach(participantId ->
                meetingParticipationRepository.save(MeetingParticipation
                        .builder()
                        .meeting(meeting)
                        .user(User.builder().userId(participantId).build())
                        .build())
        );

//        TODO: 알람 보내야 합니다.


    }
}
