package minionz.common.scrum.issue;

import minionz.common.scrum.issue.model.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT i FROM Issue i " +
            "JOIN i.workspace w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND i.status = true ")
    List<Issue> findUpcomingWorkspaceIssues(Long workspaceId, Pageable pageable);

    @Query("SELECT i FROM Issue i " +
            "WHERE i.manager.userId = :userId " +
            "AND i.status = true ")
    List<Issue> findUpcomingMyIssues(Long userId, Pageable pageable);

    @Query("SELECT COUNT(i) FROM Issue i " +
            "JOIN i.workspace w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND i.status = true ")
    int findWorkspaceIssuesCount(Long workspaceId);

    List<Issue> findByWorkspace_WorkspaceId(Long workspaceId);
}
