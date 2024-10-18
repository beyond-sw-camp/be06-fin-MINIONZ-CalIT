package minionz.apiserver.note;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.common.note.model.CursorMessage;
import minionz.common.note.model.Note;
import minionz.common.note.model.NoteMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class NoteContentController {

    private final NoteService noteService;
    private final NoteProducer noteProducer;

    // 노트 수정 메시지 처리
    @MessageMapping("/edit/{meetingId}")
    @SendTo("/topic/note/{meetingId}")
    public Note editNote(@Payload Note note, @DestinationVariable Long meetingId) throws BaseException {
        // Kafka로 메시지 전송 (optional)
        NoteMessage noteMessage = new NoteMessage();
        noteMessage.setNoteId(note.getNoteId());
        noteMessage.setNoteContents(note.getNoteContents());
        noteMessage.setSender(note.getSender());
        noteProducer.sendNoteUpdate(noteMessage);

        return note;
    }

    @MessageMapping("/save/{meetingId}")
    public void saveNote(@Payload Note note, @DestinationVariable Long meetingId) throws BaseException, IOException {
        // Note 내용을 저장
        noteService.updateNote(meetingId, note.getNoteContents(), note.getSender());
    }

    // 커서 위치 처리
    @MessageMapping("/cursor/{meetingId}")
    public CursorMessage handleCursor(@Payload CursorMessage cursorMessage, @DestinationVariable Long meetingId) {
        // cursor 정보가 null이 아닌지 확인
        if (cursorMessage.getCursor() != null) {
            // 커서 위치를 클라이언트로 전송
            return cursorMessage;
        } else {
            // Debugging 로그 추가
            System.out.println("Received null cursor information.");
        }
        return cursorMessage;
    }
}
