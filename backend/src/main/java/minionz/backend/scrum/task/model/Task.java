package minionz.backend.scrum.task.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.scrum.label_select.model.TaskLabelSelect;
import minionz.backend.scrum.task_participation.model.TaskParticipation;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.sprint.model.Sprint;

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
    public String taskContents;
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

    // Task : Meeting = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
