package minionz.backend.scrum.issue.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.scrum.issue_label_select.model.IssueLabelSelect;
import minionz.backend.scrum.issue_participation.model.IssueParticipation;
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
public class Issue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    public String issueTitle;
    public String issueContents;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public LocalDateTime doneDate;
    public int level;
    public int priority;

    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    private String issueNumber;

    // Issue : IssueParticipation = 1 : N
    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
    private List<IssueParticipation> issueParticipations = new ArrayList<>();

    // Issue : IssueLabelSelect = 1 : N
    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
    private List<IssueLabelSelect> issueLabelSelects = new ArrayList<>();

    // Issue : Sprint = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    // Issue : Meeting = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
