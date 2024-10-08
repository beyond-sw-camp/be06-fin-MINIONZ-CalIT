package minionz.backend.scrum.schedule.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IssueResponse {
    private String title;
    private String description;
    private String managerId;
}
