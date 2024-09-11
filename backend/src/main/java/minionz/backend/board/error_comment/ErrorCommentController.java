package minionz.backend.board.error_comment;

import lombok.RequiredArgsConstructor;
import minionz.backend.board.error_comment.model.request.CreateErrorCommentRequest;
import minionz.backend.board.error_comment.model.response.CreateErrorCommentResponse;
import minionz.backend.board.error_comment.model.response.GetErrorCommentResponse;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/errcomment")
public class ErrorCommentController {
    private final CloudFileUpload cloudFileUpload;
    private final ErrorCommentService errorCommentService;

    // 게시글별 댓글 작성
    @PostMapping("/write")
    public BaseResponse<CreateErrorCommentResponse> createCommentWithImages(
            @RequestParam Long errorBoardId,
            @RequestPart(name = "request") CreateErrorCommentRequest request,
            @RequestPart(name = "files") MultipartFile[] files
        ) throws BaseException {
        List<String> fileNames = cloudFileUpload.multipleUpload(files);
        CreateErrorCommentResponse response = errorCommentService.create(errorBoardId, request, fileNames);
        return new BaseResponse<>(BaseResponseStatus.ERRORCOMMENT_CREATE_SUCCESS, response);
    }
    // 게시글별 댓글 조회
    @GetMapping("/search")
    public BaseResponse<List<GetErrorCommentResponse>> getCommentsByErrorBoard(
            @RequestParam Long errorBoardId) throws BaseException {
        List<GetErrorCommentResponse> responses = errorCommentService.read(errorBoardId);
        return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, responses);
    }
}
