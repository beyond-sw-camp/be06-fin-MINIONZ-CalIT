package minionz.backend.scrum.schedule;

import lombok.RequiredArgsConstructor;
import minionz.backend.scrum.meeting.MeetingRepository;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.schedule.request.ReadScheduleRequest;
import minionz.backend.scrum.schedule.response.MeetingResponse;
import minionz.backend.scrum.schedule.response.ReadMonthlyResponse;
import minionz.backend.scrum.schedule.response.SprintResponse;
import minionz.backend.scrum.sprint.SprintRepository;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.sprint.model.response.Participant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final SprintRepository sprintRepository;
    private final MeetingRepository meetingRepository;

    public ReadMonthlyResponse readWorkspaceMonthly(Long workspaceId, ReadScheduleRequest request) {
        List<Sprint> sprintResult = sprintRepository.findSprintsInMonth(workspaceId, request.getStartDate(), request.getEndDate());
        List<SprintResponse> sprints = sprintResult.stream().map(
                sprint -> SprintResponse
                        .builder()
                        .id(sprint.getSprintId())
                        .title(sprint.getSprintTitle())
                        .startDate(sprint.getStartDate())
                        .endDate(sprint.getEndDate())
                        .build()

        ).toList();

        List<Meeting> meetingResult = meetingRepository.findMeetingsInMonth(workspaceId, request.getStartDate(), request.getEndDate());
        List<MeetingResponse> meetings = meetingResult.stream().map(
                meeting -> MeetingResponse
                        .builder()
                        .id(meeting.getMeetingId())
                        .title(meeting.getMeetingTitle())
                        .startDate(meeting.getStartDate())
                        .participants(findParticipants(meeting))
                        .build()
        ).toList();


        ReadMonthlyResponse response = ReadMonthlyResponse.builder().sprints(sprints).meetings(meetings).build();
        return response;
    }

    public List<Participant> findParticipants(Meeting meeting) {
        return meeting.getMeetingParticipations().stream().map(
                participant -> Participant.builder()
                        .id(participant.getUser().getUserId())
                        .userName(participant.getUser().getUserName())
                        .isManager(true)
                        .build()
        ).toList();
    }
}
