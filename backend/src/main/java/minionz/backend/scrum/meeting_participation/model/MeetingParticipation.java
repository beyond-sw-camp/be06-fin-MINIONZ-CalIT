package minionz.backend.scrum.meeting_participation.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.note.model.Note;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MeetingParticipation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long meetingParticipationId;

    // MeetingParticipation : User = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // MeetingParticipation : Meeting = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;
}
