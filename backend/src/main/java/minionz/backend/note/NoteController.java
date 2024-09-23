package minionz.backend.note;

import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.note.model.request.CreateNoteRequest;
import minionz.backend.note.model.response.CreateNoteResponse;
import minionz.backend.note.model.response.GetNoteResponse;
import minionz.backend.user.model.CustomSecurityUserDetails;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;
    private final CloudFileUpload cloudFileUpload;

    public NoteController(CloudFileUpload cloudFileUpload) {
        this.cloudFileUpload = cloudFileUpload;
    }

    // 회의록을 생성하고 회의와 연결하는 메서드
    @PostMapping("/create")
    public BaseResponse<CreateNoteResponse> createNote(@RequestPart CreateNoteRequest request, @RequestParam Long meetingId, @RequestPart(name = "files")MultipartFile[] files, @AuthenticationPrincipal CustomSecurityUserDetails userDetails ) throws BaseException {
        List<String> fileNames = cloudFileUpload.multipleUpload(files);
        Long userId = userDetails.getUserId();
        CreateNoteResponse response = noteService.createNote( request,meetingId,fileNames,userId);
        return new BaseResponse<>(BaseResponseStatus.NOTE_REGISTER_SUCCESS);
    }

    @GetMapping("/search")
    public BaseResponse<GetNoteResponse> searchNote(@RequestParam Long noteId) throws BaseException {
        GetNoteResponse response = noteService.readNote(noteId);
        return new BaseResponse<>(BaseResponseStatus.NOTE_SEARCH_SUCCESS,response);
    }

}

