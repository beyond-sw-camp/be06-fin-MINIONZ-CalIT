package minionz.backend.scrum.issue.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Issue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    private String issueTitle;
    private String issueContents;
    private Boolean status;

    // Issue : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // Issue : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
