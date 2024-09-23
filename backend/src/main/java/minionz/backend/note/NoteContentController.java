package minionz.backend.note;

import minionz.backend.note.model.NoteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NoteContentController {

    @Autowired
    private NoteService noteService;

    // 클라이언트에서 /app/note/edit 경로로 메시지를 보낼 때 호출됨
    @MessageMapping("/edit")
    @SendTo("/topic/note") // 메시지가 전파될 경로
    public NoteMessage editNote(NoteMessage message) throws Exception {
        // Note 내용을 업데이트
   // noteService.updateNote( message.getNoteId(), message.getNoteTitle().toString(), message.getNoteContents().toString());

        // 수정된 메시지를 반환해서 모든 구독자에게 전송
        return message;
    }
}
