package minionz.backend.scrum.issue_label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.issue.model.Issue;
import minionz.backend.scrum.issue_label.model.IssueLabel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IssueLabelSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueLabelSelectId;

    // IssueLabelSelect : IssueLabel = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_label_id")
    private IssueLabel issueLabel;

    // IssueLabelSelect : Issue = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;
}
