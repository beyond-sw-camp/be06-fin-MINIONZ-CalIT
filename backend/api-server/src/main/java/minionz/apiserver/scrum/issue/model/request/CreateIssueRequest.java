package minionz.apiserver.scrum.issue.model.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateIssueRequest {
    private String title;
    private String contents;

    private Long managerId; // 담당자
    private String assignees; // 할당자
    private String reviewers; // 리뷰어

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
