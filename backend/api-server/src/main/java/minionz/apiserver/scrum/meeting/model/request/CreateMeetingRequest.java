package minionz.apiserver.scrum.meeting.model.request;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CreateMeetingRequest {
    private String title;
    private String contents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Long> participants;
}
