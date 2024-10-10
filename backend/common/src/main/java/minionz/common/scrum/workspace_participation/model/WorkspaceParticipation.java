package minionz.common.scrum.workspace_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WorkspaceParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceParticipationId;

    private Boolean isManager;
    private Boolean isValid;

    // WorkspaceParticipation : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // WorkspaceParticipation : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
