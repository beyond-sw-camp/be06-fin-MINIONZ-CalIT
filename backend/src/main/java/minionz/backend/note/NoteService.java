package minionz.backend.note;

import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.note.model.Note;
import minionz.backend.note.model.NoteImage;
import minionz.backend.note.model.request.CreateNoteRequest;
import minionz.backend.note.model.response.CreateNoteResponse;
import minionz.backend.note.model.response.GetNoteImageResponse;
import minionz.backend.note.model.response.GetNoteResponse;
import minionz.backend.scrum.meeting.MeetingRepository;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.meeting_participation.MeetingParticipationRepository;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private NoteImageRepository noteImageRepository;
    @Autowired
    private MeetingParticipationRepository meetingParticipationRepository;

    @Transactional
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest, Long meetingId, List<String> fileNames, Long userId) {

        // Meeting, User, Workspace 존재 여부 확인
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.MEETING_NOT_EXISTS));

        MeetingParticipation participation = meetingParticipationRepository.findByUser_UserId(userId);
        // Note 생성 또는 기존 Note 수정
        Note note;

            note = new Note();
            note.setMeeting(meeting);
            note.setNoteContents(createNoteRequest.getNoteContent());


        // 회의록에 참가자 추가
        if (!note.getMeetingParticipations().contains(participation)) {
            note.getMeetingParticipations().add(participation);
        }

        noteRepository.save(note);

        // NoteImage 저장
        List<GetNoteImageResponse> getNoteImageResponseList = new ArrayList<>();
        for (String fileName : fileNames) {
            NoteImage noteImage = NoteImage.builder()
                    .imageUrl(fileName)
                    .note(note)
                    .build();
            noteImageRepository.save(noteImage);

            GetNoteImageResponse getNoteImageResponse = GetNoteImageResponse.builder()
                    .imageUrl(noteImage.getImageUrl())
                    .noteImageId(noteImage.getNoteImageId())
                    .createdAt(noteImage.getCreatedAt())
                    .modifiedAt(noteImage.getModifiedAt())
                    .build();
            getNoteImageResponseList.add(getNoteImageResponse);
        }

        // CreateNoteResponse 반환
        return CreateNoteResponse.builder()

                .noteContent(createNoteRequest.getNoteContent())
                .meetingId(meetingId)
                .build();
    }

    public GetNoteResponse readNote(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTE_REGISTER_SUCCESS));
        return GetNoteResponse.builder()
                .noteContent(note.getNoteContents())
                .noteTitle(note.getNoteTitle())
                .build();
    }
    @Transactional
    public void updateNote(Long noteId, String noteTitle, String noteContents) {
        // 노트를 ID로 조회하여 존재하지 않을 경우 예외 처리
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.TASK_NOT_EXISTS));

        // 노트 내용 업데이트
        note.setNoteTitle(noteTitle);
        note.setNoteContents(noteContents);

        // 변경된 내용 저장
        noteRepository.save(note);
    }
}
