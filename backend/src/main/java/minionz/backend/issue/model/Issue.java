package minionz.backend.issue.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.common.BaseEntity;
import minionz.backend.issue_label_select.model.IssueLabelSelect;
import minionz.backend.issue_participation.model.IssueParticipation;
import minionz.backend.meeting.model.Meeting;
import minionz.backend.sprint.model.Sprint;

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
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IssueParticipation> issueParticipations = new ArrayList<>();

    // Issue : IssueLabelSelect = 1 : N
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IssueLabelSelect> issueLabelSelects = new ArrayList<>();

    // Issue : Sprint = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    // Issue : Meeting = N : 1
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
