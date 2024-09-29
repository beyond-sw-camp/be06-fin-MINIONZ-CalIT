package minionz.backend.note.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteMessage {
    private Long noteId; // 업데이트할 노트의 ID
    private Object noteContents;
    private String sender;
}
