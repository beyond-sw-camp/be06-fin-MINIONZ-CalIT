package minionz.backend.issue_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.issue.model.Issue;
import minionz.backend.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IssueParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueParticipationId;

    // IssueParticipation : User = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // IssueParticipation : Issue = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
