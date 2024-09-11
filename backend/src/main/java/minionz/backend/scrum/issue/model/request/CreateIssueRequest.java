package minionz.backend.scrum.issue.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateIssueRequest {
    private String title;
    private String contents;
    private Long managerId;
}
