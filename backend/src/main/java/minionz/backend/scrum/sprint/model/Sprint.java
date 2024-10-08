package minionz.backend.scrum.sprint.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.scrum.label_select.model.SprintLabelSelect;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.workspace.model.Workspace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Sprint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintId;

    private String sprintTitle;
    private String sprintContents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private SprintStatus status;

    // Sprint : SprintParticipation = 1 : N
    @OneToMany(mappedBy = "sprint", fetch = FetchType.LAZY)
    private List<SprintParticipation> sprintParticipations = new ArrayList<>();

    // Sprint : SprintLabelSelect = 1 : N
    @OneToMany(mappedBy = "sprint", fetch = FetchType.LAZY)
    private List<SprintLabelSelect> sprintLabelSelects = new ArrayList<>();

    // Sprint : Meeting = 1 : N
    @OneToMany(mappedBy = "sprint", fetch = FetchType.LAZY)
    private List<Meeting> meetings = new ArrayList<>();

    // Sprint : Task = 1 : N
    @OneToMany(mappedBy = "sprint", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    // Sprint : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;
}
