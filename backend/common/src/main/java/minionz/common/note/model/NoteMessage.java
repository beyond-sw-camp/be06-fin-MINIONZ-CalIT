package minionz.common.note.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteMessage {
    private Long noteId;
    private String noteContents;
    private String sender;
    private CursorPosition cursor;  // 커서 정보 추가
}
