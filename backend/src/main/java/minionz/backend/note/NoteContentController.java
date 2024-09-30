package minionz.backend.note;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.note.model.Note;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NoteContentController {

    private final NoteService noteService;

    // 클라이언트에서 /app/note/edit 경로로 메시지를 보낼 때 호출됨
    @MessageMapping("/edit/{meetingId}")
    @SendTo("/topic/note") // 메시지가 전파될 경로
    public Note editNote(Note note,@DestinationVariable Long meetingId) throws BaseException {
        // Note 내용을 업데이트
    noteService.updateNote(meetingId, note.getNoteContents().toString());

        // 수정된 메시지를 반환해서 모든 구독자에게 전송
        return note;
    }
}
