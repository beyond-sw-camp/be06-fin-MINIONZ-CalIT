package minionz.backend.scrum.meeting.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CreateMeetingResponse {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
