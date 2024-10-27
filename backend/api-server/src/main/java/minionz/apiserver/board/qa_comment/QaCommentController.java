package minionz.apiserver.board.qa_comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qacomment")
public class QaCommentController {

  private final CloudFileUpload cloudFileUpload;
  private final QaCommentService qaCommentService;
  private final ObjectMapper objectMapper;  // ObjectMapper 주입

  // 게시글별 댓글 작성
  @PostMapping("/write")
  public BaseResponse<CreateQaCommentResponse> createCommentWithImages(
          @RequestParam Long qaBoardId,
          @RequestPart(name = "request") String requestJson,  // JSON 데이터를 문자열로 받음
          @RequestPart(name = "files", required = false) MultipartFile[] files,  // 파일을 선택적으로 받음
          @AuthenticationPrincipal CustomSecurityUserDetails userDetails) throws BaseException {

    // JSON 문자열을 객체로 변환
    CreateQaCommentRequest request;

    try {
      request = objectMapper.readValue(requestJson, CreateQaCommentRequest.class);
    } catch (JsonProcessingException e) {
      throw new BaseException(BaseResponseStatus.QACOMMENT_CREATE_FAIL);
    }

    // 파일 업로드 처리
    List<String> fileNames = (files != null && files.length > 0)
            ? cloudFileUpload.multipleUpload(files)
            : Collections.emptyList();  // 파일이 없으면 빈 리스트 반환

    Long userId = userDetails.getUserId();

    // 댓글 생성 서비스 호출
    CreateQaCommentResponse response = qaCommentService.create(qaBoardId, request, fileNames, userId);

    return new BaseResponse<>(BaseResponseStatus.QACOMMENT_CREATE_SUCCESS, response);
  }

  // 게시글별 댓글 조회
  @GetMapping("/search")
  public BaseResponse<List<GetQaCommentResponse>> getCommentsByQaBoard(
          @RequestParam Long qaBoardId) throws BaseException {

    List<GetQaCommentResponse> responses = qaCommentService.read(qaBoardId);
    return new BaseResponse<>(BaseResponseStatus.QACOMMENT_SEARCH_SUCCESS, responses);
  }
}
