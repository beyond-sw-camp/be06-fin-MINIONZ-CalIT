package minionz.backend.scrum.issue_label.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.issue_label_select.model.IssueLabelSelect;
import minionz.backend.scrum.workspace.model.Workspace;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class IssueLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueLabelId;

    private String labelName;
    private String description;
    private String color;

    // IssueLabel : IssueLabelSelect = 1 : N
    @OneToMany(mappedBy = "issueLabel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IssueLabelSelect> issueLabelSelects = new ArrayList<>();

    // IssueLabel : Workspace = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
