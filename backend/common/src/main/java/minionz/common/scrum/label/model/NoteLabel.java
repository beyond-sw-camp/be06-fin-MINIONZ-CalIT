package minionz.common.scrum.label.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.label_select.model.NoteLabelSelect;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.workspace.model.Workspace;

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

    // NoteLabel : NoteLabelSelect = 1 : N
    @OneToMany(mappedBy = "noteLabel", fetch = FetchType.LAZY)
    private List<NoteLabelSelect> noteLabelSelects = new ArrayList<>();

    // NoteLabel : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    // NoteLabel : meeting = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
