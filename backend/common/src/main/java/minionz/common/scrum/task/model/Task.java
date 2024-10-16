package minionz.common.scrum.task.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.label_select.model.TaskLabelSelect;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.task_participation.model.TaskParticipation;
import minionz.common.common.BaseEntity;
import minionz.common.scrum.workspace.model.Workspace;
import org.hibernate.jdbc.Work;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Task extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    public String taskTitle;

    @Column(name = "task_contents", columnDefinition = "LONGTEXT")
    private String taskContents;

    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public LocalDateTime doneDate;

    @Enumerated(EnumType.STRING)
    public TaskLevel difficultly;
    @Enumerated(EnumType.STRING)
    public TaskLevel priority;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String taskNumber;

    // Task : TaskParticipation = 1 : N
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskParticipation> taskParticipations = new ArrayList<>();

    // Task : TaskParticipation = 1 : N
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskLabelSelect> taskLabelSelects = new ArrayList<>();

    // Task : Sprint = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    // Task : Workspace = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;


    // Task : Meeting = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
