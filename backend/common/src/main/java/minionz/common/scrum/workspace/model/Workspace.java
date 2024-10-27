package minionz.common.scrum.workspace.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.issue.model.Issue;
import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.label.model.SprintLabel;
import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.board.qa_board.model.QaBoard;

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
    private int avatar;

    // Workspace : Sprint = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Sprint> sprints = new ArrayList<>();

    // Workspace : Task = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();


    // Workspace : WorkspaceParticipation = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<WorkspaceParticipation> workspaceParticipations = new ArrayList<>();

    // Workspace : SprintLabel = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<SprintLabel> sprintLabels = new ArrayList<>();

    // Workspace : Issue = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<Issue> issues = new ArrayList<>();

    // Workspace : TaskLabel = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<TaskLabel> taskLabels = new ArrayList<>();

    // Workspace : NoteLabel = 1 : N
    @OneToMany(mappedBy = "workspace", fetch = FetchType.LAZY)
    private List<NoteLabel> noteLabels = new ArrayList<>();

    // Workspace : errorBoard = 1 : 1
    @OneToMany(mappedBy = "workSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ErrorBoard> errorBoards = new ArrayList<>();

    @OneToMany(mappedBy = "workSpace", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<QaBoard> qaBoards = new ArrayList<>();
}
