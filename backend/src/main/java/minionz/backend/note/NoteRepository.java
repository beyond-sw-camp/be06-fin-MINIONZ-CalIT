package minionz.backend.note;

import minionz.backend.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findByMeeting_MeetingId(Long meetingId);// 연관 관계의 필드명을 정확히 사용
    Note findByNoteId(Long noteId);
}


