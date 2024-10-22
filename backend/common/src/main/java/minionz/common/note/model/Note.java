package minionz.common.note.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.meeting_participation.model.MeetingParticipation;
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

    @Column(name = "note_contents", columnDefinition = "LONGTEXT")
    private String noteContents;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY)
    private List<MeetingParticipation> meetingParticipations = new ArrayList<>();

    private String sender;





}