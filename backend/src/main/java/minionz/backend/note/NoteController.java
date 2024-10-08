package minionz.backend.note;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.note.model.response.GetNoteAllResponse;
import minionz.backend.note.model.response.GetNoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

  private final NoteService noteService;

  @GetMapping("/search")
  public BaseResponse<GetNoteResponse> searchNote(@RequestParam Long noteId) throws BaseException {
    GetNoteResponse response = noteService.readNote(noteId);
    return new BaseResponse<>(BaseResponseStatus.NOTE_SEARCH_SUCCESS, response);
  }

  @GetMapping("/search-all")
  public BaseResponse<Page<GetNoteAllResponse>> readAllErrorBoard(

          @RequestParam int page,
          @RequestParam int size) throws BaseException {
    Page<GetNoteAllResponse> response = noteService.readAll(page, size);
    return new BaseResponse<>(BaseResponseStatus.NOTE_SEARCH_SUCCESS, response);
  }
}
