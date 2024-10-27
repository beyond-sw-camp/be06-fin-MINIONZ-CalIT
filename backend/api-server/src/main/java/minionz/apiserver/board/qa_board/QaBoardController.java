package minionz.apiserver.board.qa_board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.board.qa_board.model.request.CreateQaBoardRequest;
import minionz.apiserver.board.qa_board.model.response.CreateQaBoardResponse;
import minionz.apiserver.board.qa_board.model.response.GetQaBoardResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.user.model.CustomSecurityUserDetails;
import minionz.apiserver.utils.CloudFileUpload;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qaboard")
public class QaBoardController {
  private final QaBoardService qaBoardService;
  private final CloudFileUpload cloudFileUpload;

  @PostMapping("/write")
  public BaseResponse<CreateQaBoardResponse> createQaBoard(
          @RequestParam Long workspaceId,
          @RequestPart(name = "request") String requestJson,  // JSON 데이터를 문자열로 받음
          @RequestPart(name = "files", required = false) MultipartFile[] files,
          @AuthenticationPrincipal CustomSecurityUserDetails userDetails) throws BaseException {

    // JSON을 객체로 변환
    ObjectMapper objectMapper = new ObjectMapper();
    CreateQaBoardRequest request;
    try {
      request = objectMapper.readValue(requestJson, CreateQaBoardRequest.class);
    } catch (JsonProcessingException e) {
      throw new BaseException(BaseResponseStatus.QABOARD_CREATE_FAIL);
    }

    // 파일 처리
    List<String> fileNames = (files != null && files.length > 0) ? cloudFileUpload.multipleUpload(files) : Collections.emptyList();

    Long userId = userDetails.getUserId();
    CreateQaBoardResponse response = qaBoardService.create(fileNames, request, workspaceId, userId);
    return new BaseResponse<>(BaseResponseStatus.QABOARD_CREATE_SUCCESS, response);
  }


  // 게시글 하나 조회
  @GetMapping("/search")
  public BaseResponse<GetQaBoardResponse> search(
      @RequestParam Long boardId) throws BaseException {
    GetQaBoardResponse response = qaBoardService.read(boardId);
    return new BaseResponse<>(BaseResponseStatus.QABOARD_SEARCH_SUCCESS, response);
  }

  // 게시글 전체 조회
  @GetMapping("/search-all")
  public BaseResponse<Page<GetQaBoardResponse>> readAllQaBoard(
          @RequestParam Long workspaceId,
      @RequestParam int page,
      @RequestParam int size) throws BaseException {
    Page<GetQaBoardResponse> response = qaBoardService.readAll(workspaceId,page, size);
    return new BaseResponse<>(BaseResponseStatus.QABOARD_SEARCH_SUCCESS, response);
  }

  // 단어별 조회
  @GetMapping("/search-keyword")
  public BaseResponse<Page<GetQaBoardResponse>> readKeyword(
          @RequestParam Long workspaceId,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String keyword) throws BaseException {
    Page<GetQaBoardResponse> response = qaBoardService.readKeyword(workspaceId,keyword, page, size);
    return new BaseResponse<>(BaseResponseStatus.QABOARD_SEARCH_SUCCESS, response);
  }
}
