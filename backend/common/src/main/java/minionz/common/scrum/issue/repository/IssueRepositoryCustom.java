package minionz.common.scrum.issue.repository;


import minionz.common.scrum.issue.model.Issue;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IssueRepositoryCustom {
    List<Issue> findUpcomingWorkspaceIssues(Long workspaceId, Pageable pageable);
    List<Issue> findUpcomingMyIssues(Long userId, Pageable pageable);
    int findWorkspaceIssuesCount(Long workspaceId);
}