package minionz.apiserver.note;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.note.model.response.GetNoteAllResponse;
import minionz.apiserver.note.model.response.GetNoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

  private final NoteService noteService;

  // 회의록 내용을 Redis에서 DB로 저장하는 API
  @PostMapping("/save")
  public BaseResponse<String> saveNoteToDB(@RequestParam Long meetingId) {
    try {
      noteService.saveNoteToDB(meetingId);
      return new BaseResponse<>(BaseResponseStatus.NOTE_SAVE_SUCCESS);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }
  }
  @GetMapping("/search")
  public BaseResponse<GetNoteResponse> searchNote(@RequestParam Long noteId) throws BaseException, JsonProcessingException {
    GetNoteResponse response = noteService.readNote(noteId);
    return new BaseResponse<>(BaseResponseStatus.NOTE_SEARCH_SUCCESS, response);
  }
  @GetMapping("/load")
  public BaseResponse<GetNoteResponse> loadNoteContent(@RequestParam Long meetingId) {
    try {
      GetNoteResponse noteResponse = noteService.readNote(meetingId);
      return new BaseResponse<>(BaseResponseStatus.NOTE_SAVE_SUCCESS, noteResponse);
    } catch (BaseException e) {
      return new BaseResponse<>(BaseResponseStatus.NOTE_NOT_FOUND);
    }
  }

  @GetMapping("/search-all")
  public BaseResponse<Page<GetNoteAllResponse>> readAllNote(

          @RequestParam int page,
          @RequestParam int size) throws BaseException {
    Page<GetNoteAllResponse> response = noteService.readAll(page, size);
    return new BaseResponse<>(BaseResponseStatus.NOTE_SEARCH_SUCCESS, response);
  }
}