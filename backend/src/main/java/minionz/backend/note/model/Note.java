package minionz.backend.note.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.label_select.model.NoteLabelSelect;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private String noteTitle;
    private String noteContents;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY)
    private List<NoteLabelSelect> noteLabelSelects = new ArrayList<>();

    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();




}
