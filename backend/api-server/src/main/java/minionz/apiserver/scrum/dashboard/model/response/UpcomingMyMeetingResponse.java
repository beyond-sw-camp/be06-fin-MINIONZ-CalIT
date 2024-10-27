package minionz.apiserver.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpcomingMyMeetingResponse {
    private Long id;
    private String title;
    private String workspaceName;
    private String sprintName;
    private LocalDateTime startDate;
}
