package minionz.apiserver.board.qa_comment;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.board.qa_comment.model.request.CreateQaCommentRequest;
import minionz.apiserver.board.qa_comment.model.response.CreateQaCommentResponse;
import minionz.apiserver.board.qa_comment.model.response.GetQaCommentResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import minionz.apiserver.utils.CloudFileUpload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qacomment")
public class QaCommentController {
  private final CloudFileUpload cloudFileUpload;
  private final QaCommentService qaCommentService;

  // 게시글별 댓글 작성
  @PostMapping("/write")
  public BaseResponse<CreateQaCommentResponse> createCommentWithImages(
      @RequestParam Long qaBoardId,
      @RequestPart(name = "request") CreateQaCommentRequest request,
      @RequestPart(name = "files") MultipartFile[] files,
      @AuthenticationPrincipal CustomSecurityUserDetails userDetails) throws BaseException {
    List<String> fileNames = cloudFileUpload.multipleUpload(files);
    Long userId = userDetails.getUserId();
    CreateQaCommentResponse response = qaCommentService.create(qaBoardId, request, fileNames, userId);
    return new BaseResponse<>(BaseResponseStatus.QACOMMENT_CREATE_SUCCESS, response);
  }

  // 게시글별 댓글 조회
  @GetMapping("/search")
  public BaseResponse<List<GetQaCommentResponse>> getCommentsByErrorBoard(
      @RequestParam Long qaBoardId) throws BaseException {
    List<GetQaCommentResponse> responses = qaCommentService.read(qaBoardId);
    return new BaseResponse<>(BaseResponseStatus.QACOMMENT_CREATE_SUCCESS, responses);
  }

}
