package minionz.backend.scrum.issue;

import minionz.backend.scrum.issue.model.Issue;
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
            "WHERE i.user.userId = :userId " +
            "AND i.status = true ")
    List<Issue> findUpcomingMyIssues(Long userId, Pageable pageable);
}
