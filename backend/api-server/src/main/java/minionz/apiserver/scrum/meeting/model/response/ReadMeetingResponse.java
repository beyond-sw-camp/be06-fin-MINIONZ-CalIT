package minionz.apiserver.scrum.meeting.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.apiserver.scrum.sprint.model.response.Participant;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadMeetingResponse {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Participant> participants;
    private LocalDateTime createdAt;
}
