package minionz.backend.scrum.schedule.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.scrum.sprint.model.response.Participant;

@Builder
@Getter
public class IssueResponse {
    private String title;
    private String description;
    private String managerId;
}
