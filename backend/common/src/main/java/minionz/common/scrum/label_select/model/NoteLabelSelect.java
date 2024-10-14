package minionz.common.scrum.label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.meeting.model.Meeting;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class NoteLabelSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteLabelSelectId;

    // NoteLabelSelect : NoteLabel = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_label_id")
    private NoteLabel noteLabel;


    // NoteLabelSelect : Note = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}