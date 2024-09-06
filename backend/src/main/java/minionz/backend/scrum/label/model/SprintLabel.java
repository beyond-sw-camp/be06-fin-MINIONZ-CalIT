package minionz.backend.scrum.label.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.sprint_label_select.model.SprintLabelSelect;
import minionz.backend.scrum.workspace.model.Workspace;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintLabelId;

    private String labelName;
    private String description;
    private String color;

    // SprintLabel : SprintLabelSelect = 1 : N
    @OneToMany(mappedBy = "sprintLabel", fetch = FetchType.LAZY)
    private List<SprintLabelSelect> sprintLabelSelects = new ArrayList<>();

    // SprintLabel : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
