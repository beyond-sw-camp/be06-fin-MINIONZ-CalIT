package minionz.apiserver.note;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.note.model.response.GetNoteAllResponse;
import minionz.apiserver.note.model.response.GetNoteResponse;
import minionz.common.note.NoteRepository;
import minionz.common.note.model.Note;
import minionz.common.scrum.meeting.MeetingRepository;
import minionz.common.scrum.meeting.model.Meeting;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final MeetingRepository meetingRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // 노트 읽기 - 캐시 먼저 확인
    @Cacheable(value = "notes", key = "#noteId")
    public GetNoteResponse readNote(Long noteId) {
        String redisKey = "noteContent:" + noteId;
        String noteContent = redisTemplate.opsForValue().get(redisKey);

        if (noteContent == null) {
            Note note = noteRepository.findById(noteId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOTE_NOT_FOUND));

            // Redis에 회의록 내용만 캐시 저장 (만료 시간 없이)
            noteContent = note.getNoteContents();
            redisTemplate.opsForValue().set(redisKey, noteContent); // 만료 시간 없이 저장
        }

        return GetNoteResponse.builder()
                .noteContent(noteContent)
                .build();
    }

    @Transactional
    public void updateNote(Long meetingId, String noteContents, String sender) {
        String redisKey = "noteContent:" + meetingId;

        // Redis에 회의록 내용과 sender 정보를 저장 (만료 시간 없이)
        redisTemplate.opsForValue().set(redisKey, noteContents);
        String senderKey = "noteSender:" + meetingId;
        redisTemplate.opsForValue().set(senderKey, sender);
    }

    // 캐시에서 내용 가져와서 DB에 저장
    // 캐시에서 내용 가져와서 DB에 저장
    @CacheEvict(value = "notes", key = "#meetingId")
    public void saveNoteToDB(Long meetingId) {
        String redisKey = "noteContent:" + meetingId;
        String senderKey = "noteSender:" + meetingId; // sender 저장용 key
        String cachedNoteContent = redisTemplate.opsForValue().get(redisKey);
        String cachedSender = redisTemplate.opsForValue().get(senderKey); // Redis에서 sender 가져오기

        if (cachedNoteContent != null && cachedSender != null) { // sender도 체크
            // DB에 저장하고 캐시에서 삭제
            Note note = noteRepository.findByMeeting_MeetingId(meetingId);
            if (note != null) {
                note.setNoteContents(cachedNoteContent);
                note.setSender(cachedSender); // sender 정보 저장
                noteRepository.save(note);
            } else {
                // 만약 Note가 존재하지 않으면 새로 생성하여 저장
                Meeting meeting = meetingRepository.findById(meetingId)
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.MEETING_NOT_EXISTS));

                note = new Note();
                note.setMeeting(meeting);
                note.setNoteContents(cachedNoteContent);
                note.setSender(cachedSender); // sender 정보 저장
                noteRepository.save(note);
            }

        }
    }

    public Page<GetNoteAllResponse> readAll(int page, int size) {
        Page<Note> result = noteRepository.findAll(PageRequest.of(page, size));
        return result.map(note -> GetNoteAllResponse.builder()
                .noteContent(note.getNoteContents())
                .build());
    }
}