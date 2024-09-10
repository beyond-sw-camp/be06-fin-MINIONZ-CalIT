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
    List<Issue> findUpcomingIssues(Long workspaceId, Pageable pageable);
}
