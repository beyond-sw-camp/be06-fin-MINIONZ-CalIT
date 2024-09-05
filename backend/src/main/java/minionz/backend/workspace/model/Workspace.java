package minionz.backend.workspace.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.issue_label.model.IssueLabel;
import minionz.backend.sprint.model.Sprint;
import minionz.backend.sprint_label.model.SprintLabel;
import minionz.backend.workspace_participation.model.WorkspaceParticipation;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workspaceId;

    private String workspaceName;

    // Workspace : Sprint = 1 : N
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sprint> sprints = new ArrayList<>();

    // Workspace : WorkspaceParticipation = 1 : N
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkspaceParticipation> workspaceParticipations = new ArrayList<>();

    // Workspace : SprintLabel = 1 : N
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SprintLabel> sprintLabels = new ArrayList<>();

    // Workspace : IssueLabel = 1 : N
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IssueLabel> issueLabels = new ArrayList<>();
}
