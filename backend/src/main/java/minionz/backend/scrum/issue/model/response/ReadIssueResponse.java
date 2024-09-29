package minionz.backend.scrum.issue.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadIssueResponse {
    private String title;
    private String contents;

    private String reviewers;
    private String assignees;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private boolean status;
    // 1이 InProgress, 0이 Done
}
