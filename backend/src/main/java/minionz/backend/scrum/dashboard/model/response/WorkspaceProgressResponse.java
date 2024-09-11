package minionz.backend.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WorkspaceProgressResponse {
    private int sprintCount;
    private int allSprintCount;
    private int allTaskCount;
    private int successTaskCount;
    private int issueCount;
}
