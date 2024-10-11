package minionz.apiserver.scrum.meeting.model.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateMeetingResponse {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}