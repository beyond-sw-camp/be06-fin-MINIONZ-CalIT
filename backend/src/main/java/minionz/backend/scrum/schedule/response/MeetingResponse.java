package minionz.backend.scrum.schedule.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.scrum.sprint.model.response.Participant;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MeetingResponse {
    private Long id;
    private String title;
    private LocalDateTime startDate;
    private List<Participant> participants;
}
