package minionz.backend.scrum.issue;

import minionz.backend.scrum.issue.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
}
