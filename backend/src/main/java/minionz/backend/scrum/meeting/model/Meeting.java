package minionz.backend.scrum.meeting.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.scrum.label.model.NoteLabel;
import minionz.backend.scrum.label_select.model.NoteLabelSelect;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.task.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Meeting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String meetingTitle;
    private String meetingContents;

    // Meeting : MeetingParticipation = 1 : N
    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();

    // Meeting : Task = 1 : N
    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    // Meeting : NoteLabel = 1 : N
    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<NoteLabel> noteLabels =new ArrayList<>();

    // Sprint : SprintLabelSelect = 1 : N
    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<NoteLabelSelect> noteLabelSelects = new ArrayList<>();

    // Meeting : Sprint = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
