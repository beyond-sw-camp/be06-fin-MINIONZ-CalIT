package minionz.apiserver.scrum.meeting;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.meeting.model.request.CreateMeetingRequest;
import minionz.apiserver.scrum.meeting.model.response.CreateMeetingResponse;
import minionz.apiserver.scrum.meeting.model.response.ReadAllMeetingResponse;
import minionz.apiserver.scrum.meeting.model.response.ReadMeetingResponse;
import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.label_select.NoteLabelSelectRepository;
import minionz.common.scrum.label_select.model.NoteLabelSelect;
import minionz.common.scrum.meeting_participation.MeetingParticipationRepository;
import minionz.common.scrum.meeting_participation.model.MeetingParticipation;
import minionz.apiserver.scrum.sprint.model.response.Label;
import minionz.apiserver.scrum.sprint.model.response.Participant;
import minionz.common.scrum.meeting.MeetingRepository;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingParticipationRepository meetingParticipationRepository;
    private final NoteLabelSelectRepository noteLabelSelectRepository;

    @Transactional
    public CreateMeetingResponse createMeeting(User user, CreateMeetingRequest request, Long sprintId) {
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

        List<Long> meetings = request.getLabels();

        if(request.getLabels() == null){
            meetings = new ArrayList<>();
        }

        meetingParticipationRepository.save(MeetingParticipation.builder().meeting(meeting).user(user).build());
        meetings.forEach(participantId ->
                meetingParticipationRepository.save(MeetingParticipation
                        .builder()
                        .meeting(meeting)
                        .user(User.builder().userId(participantId).build())
                        .build())
        );

        List<Long> labels = request.getLabels();

        if(request.getLabels() == null){
            labels = new ArrayList<>();
        }

        labels.forEach(labelId ->
                noteLabelSelectRepository.save(NoteLabelSelect.builder()
                        .meeting(Meeting.builder().meetingId(meeting.getMeetingId()).build())
                        .noteLabel(NoteLabel.builder()
                                .noteLabelId(labelId)
                                .build())
                        .build())
        );


        return CreateMeetingResponse.builder()
                .id(meeting.getMeetingId())
                .title(meeting.getMeetingTitle())
                .contents(meeting.getMeetingContents())
                .endDate(meeting.getEndDate())
                .startDate(meeting.getStartDate())
                .build();
    }

    public ReadMeetingResponse readMeeting(Long meetingId) throws BaseException {
        Optional<Meeting> result = meetingRepository.findById(meetingId);

        if (result.isEmpty()) {
            throw new BaseException(BaseResponseStatus.MEETING_NOT_EXISTS);
        }
        Meeting meeting = result.get();

        return ReadMeetingResponse
                .builder()
                .id(meeting.getMeetingId())
                .title(meeting.getMeetingTitle())
                .contents(meeting.getMeetingContents())
                .startDate(meeting.getStartDate())
                .endDate(meeting.getEndDate())
                .createdAt(meeting.getCreatedAt())
                .participants(findParticipants(meeting))
                .labels(findLabels(meeting))
                .build();
    }


    public List<Participant> findParticipants(Meeting meeting) {
        return meeting.getMeetingParticipations().stream().map(
                participant -> Participant.builder()
                        .id(participant.getUser().getUserId())
                        .userName(participant.getUser().getUserName())
                        .persona(participant.getUser().getPersona())
                        .isManager(true)
                        .build()
        ).toList();
    }


    public Page<ReadAllMeetingResponse> readAll(Long workspaceId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Meeting> result = meetingRepository.findMeetingByWorkspace(workspaceId, pageable);

        Page<ReadAllMeetingResponse> readMeetingResponses = result.map(meeting -> {
            List<Participant> participants = findParticipants(meeting);
            int participantCount = participants.size();  // 참여자 명수 계산

            return ReadAllMeetingResponse.builder()
                    .id(meeting.getMeetingId())
                    .title(meeting.getMeetingTitle())
                    .contents(meeting.getMeetingContents())
                    .startDate(meeting.getStartDate())
                    .endDate(meeting.getEndDate())
                    .createdAt(meeting.getCreatedAt())
                    .participants(participants)
                    .participantCount(participantCount)// 참여자 명수 추가
                    .labels(findLabels(meeting))
                    .build();
        });

        return readMeetingResponses;
    }

    public List<Label> findLabels(Meeting meeting) {
        return meeting.getNoteLabelSelects().stream().map(
                label -> Label
                        .builder()
                        .id(label.getNoteLabel().getNoteLabelId())
                        .labelName(label.getNoteLabel().getLabelName())
                        .color(label.getNoteLabel().getColor())
                        .build()
        ).toList();
    }
}
