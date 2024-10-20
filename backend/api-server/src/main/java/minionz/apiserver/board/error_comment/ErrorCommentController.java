package minionz.apiserver.board.error_comment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.board.error_comment.model.request.CreateErrorCommentRequest;
import minionz.apiserver.board.error_comment.model.response.CreateErrorCommentResponse;
import minionz.apiserver.board.error_comment.model.response.GetErrorCommentResponse;
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
@RequestMapping("/errcomment")
public class ErrorCommentController {

  private final CloudFileUpload cloudFileUpload;
  private final ErrorCommentService errorCommentService;
  private final ObjectMapper objectMapper;  // ObjectMapper 주입

  // 게시글별 댓글 작성
  @PostMapping("/write")
  public BaseResponse<CreateErrorCommentResponse> createCommentWithImages(
          @RequestParam Long errorBoardId,
          @RequestPart(name = "request") String requestJson,  // JSON 데이터를 문자열로 받음
          @RequestPart(name = "files", required = false) MultipartFile[] files,  // 파일을 선택적으로 받음
          @AuthenticationPrincipal CustomSecurityUserDetails userDetails) throws BaseException {

    // JSON 문자열을 객체로 변환
    CreateErrorCommentRequest request;
    try {
      request = objectMapper.readValue(requestJson, CreateErrorCommentRequest.class);
    } catch (JsonProcessingException e) {
      throw new BaseException(BaseResponseStatus.ERRORCOMMENT_CREATE_FAIL);
    }

    // 파일 업로드 처리
    List<String> fileNames = (files != null && files.length > 0)
            ? cloudFileUpload.multipleUpload(files)
            : Collections.emptyList();  // 파일이 없으면 빈 리스트 반환

    Long userId = userDetails.getUserId();

    // 댓글 생성 서비스 호출
    CreateErrorCommentResponse response = errorCommentService.create(errorBoardId, request, fileNames, userId);

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
