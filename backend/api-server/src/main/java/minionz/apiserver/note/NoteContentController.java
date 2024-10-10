package minionz.apiserver.note;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.common.note.model.Note;
import minionz.common.note.model.NoteMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NoteContentController {

    private final NoteService noteService;
    private final NoteProducer noteProducer;

    @MessageMapping("/edit/{meetingId}")
    @SendTo("/topic/note")
    public Note editNote(Note note, @DestinationVariable Long meetingId) throws BaseException {
        // Note 내용을 업데이트
        noteService.updateNote(meetingId, note.getNoteContents().toString());

        // Kafka로 메시지 전송
        NoteMessage noteMessage = new NoteMessage();
        noteMessage.setNoteId(note.getNoteId());
        noteMessage.setNoteContents(note.getNoteContents());
        noteMessage.setSender(note.getMeetingParticipations().toString());

        noteProducer.sendNoteUpdate(noteMessage);

        return note;
    }
}

