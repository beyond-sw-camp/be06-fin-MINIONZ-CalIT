package minionz.backend.note;

import minionz.backend.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Note findByNoteId(Long noteId);
    // 새로운 findByMeetingId 메서드 추가 (Meeting의 meetingId로 Note 검색)
    Note findByMeeting_MeetingId(Long meetingId);
}


