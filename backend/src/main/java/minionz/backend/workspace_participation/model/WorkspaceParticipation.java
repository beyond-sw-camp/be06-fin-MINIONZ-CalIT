package minionz.backend.workspace_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.user.model.User;
import minionz.backend.workspace.model.Workspace;

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

    // WorkspaceParticipation : User = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // WorkspaceParticipation : Workspace = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
