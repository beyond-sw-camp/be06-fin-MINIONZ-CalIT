package minionz.backend.scrum.task_label.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.task_label_select.model.TaskLabelSelect;
import minionz.backend.scrum.workspace.model.Workspace;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskLabelId;

    private String taskName;
    private String description;
    private String color;

    // TaskLabel : TaskLabelSelect = 1 : N
    @OneToMany(mappedBy = "taskLabel", fetch = FetchType.LAZY)
    private List<TaskLabelSelect> taskLabelSelects = new ArrayList<>();

    // TaskLabel : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
