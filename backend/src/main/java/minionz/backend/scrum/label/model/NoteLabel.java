package minionz.backend.scrum.label.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.label_select.model.NoteLabelSelect;
import minionz.backend.scrum.workspace.model.Workspace;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteLabelId;

    private String labelName;
    private String description;
    private String color;

    // SprintLabel : SprintLabelSelect = 1 : N
    @OneToMany(mappedBy = "noteLabel", fetch = FetchType.LAZY)
    private List<NoteLabelSelect> noteLabelSelects = new ArrayList<>();

    // SprintLabel : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
