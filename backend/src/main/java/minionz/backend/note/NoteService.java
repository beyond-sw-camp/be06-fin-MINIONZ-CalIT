package minionz.backend.note;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.note.model.Note;
import minionz.backend.note.model.response.GetNoteResponse;
import minionz.backend.scrum.meeting.MeetingRepository;
import minionz.backend.scrum.meeting.model.Meeting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final MeetingRepository meetingRepository;


    public GetNoteResponse readNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTE_REGISTER_SUCCESS));
        return GetNoteResponse.builder()
                .noteContent(note.getNoteContents())
                .build();
    }

    @Transactional
    public void updateNote(Long meetingId, String noteContents) {
        // 회의 ID로 노트를 조회
        Note note = noteRepository.findByMeeting_MeetingId(meetingId);

        if (note == null) {
            // 노트가 없으면 새로운 노트를 생성
            Meeting meeting = meetingRepository.findById(meetingId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.MEETING_NOT_EXISTS)); // Meeting이 존재하지 않으면 예외 발생

            note = new Note();
            note.setMeeting(meeting);
            note.setNoteContents(noteContents);

            // 새로 생성된 노트 저장
            noteRepository.save(note);
        } else {
            // 기존 노트가 있으면 내용 업데이트
            note.setNoteContents(noteContents);

            // 변경된 내용 저장
            noteRepository.save(note);
        }
    }

}

