package minionz.common.scrum.label.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.label_select.model.TaskLabelSelect;
import minionz.common.scrum.workspace.model.Workspace;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskLabelId;

    private String labelName;
    private String description;
    private Integer color;

    // TaskLabel : TaskLabelSelect = 1 : N
    @OneToMany(mappedBy = "taskLabel", fetch = FetchType.LAZY)
    private List<TaskLabelSelect> taskLabelSelects = new ArrayList<>();

    // TaskLabel : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
