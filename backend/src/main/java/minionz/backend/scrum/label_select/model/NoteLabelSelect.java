package minionz.backend.scrum.label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.note.model.Note;
import minionz.backend.scrum.label.model.NoteLabel;


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
    @JoinColumn(name = "note_id")
    private Note note;
}