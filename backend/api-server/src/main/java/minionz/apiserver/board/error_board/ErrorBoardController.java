package minionz.apiserver.board.error_board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.board.error_board.model.request.CreateErrorBoardRequest;
import minionz.apiserver.board.error_board.model.response.CreateErrorBoardResponse;
import minionz.apiserver.board.error_board.model.response.GetErrorBoardResponse;
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
@RequestMapping("/errboard")
public class ErrorBoardController {
  private final ErrorBoardService errBoardService;
  private final CloudFileUpload cloudFileUpload;

  // 게시글 생성
  @PostMapping("/write")
  public BaseResponse<CreateErrorBoardResponse> createErrorBoard(
          @RequestParam Long workspaceId,
          @RequestPart(name = "request") String requestJson,  // JSON 데이터를 문자열로 받음
          @RequestPart(name = "files", required = false) MultipartFile[] files,
          @AuthenticationPrincipal CustomSecurityUserDetails userDetails) throws BaseException {

    // JSON을 객체로 변환
    ObjectMapper objectMapper = new ObjectMapper();
    CreateErrorBoardRequest request;
    try {
      request = objectMapper.readValue(requestJson, CreateErrorBoardRequest.class);
    } catch (JsonProcessingException e) {
      throw new BaseException(BaseResponseStatus.ERRORBOARD_CREATE_FAIL);
    }

    // 파일이 제대로 전송되었는지 확인
    if (files != null) {
      for (MultipartFile file : files) {
        System.out.println("받은 파일 이름: " + file.getOriginalFilename());
      }
    }

    Long userId = userDetails.getUserId();
    List<String> fileNames = (files != null && files.length > 0) ? cloudFileUpload.multipleUpload(files) : Collections.emptyList();
    CreateErrorBoardResponse response = errBoardService.create(fileNames, request, workspaceId, userId);
    return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_CREATE_SUCCESS, response);
  }


  // 게시글 하나 조회
  @GetMapping("/search")
  public BaseResponse<GetErrorBoardResponse> search(
          @RequestParam Long boardId) throws BaseException {

    GetErrorBoardResponse response = errBoardService.read(boardId);
    return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response );
  }

  // 게시글 전체 조회
  @GetMapping("/search-all")
  public BaseResponse<Page<GetErrorBoardResponse>> readAllErrorBoard(
          @RequestParam Long workspaceId,
      @RequestParam int page,
      @RequestParam int size) throws BaseException {

    Page<GetErrorBoardResponse> response = errBoardService.readAll(workspaceId,page, size);
    return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
  }

  // 단어별 조회
  @GetMapping("/search-keyword")
  public BaseResponse<Page<GetErrorBoardResponse>> readKeyword(
          @RequestParam Long workspaceId,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String keyword) throws BaseException {

    Page<GetErrorBoardResponse> response = errBoardService.readKeyword(workspaceId,keyword, page, size);
    return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
  }

  // 카테고리별 조회
  @GetMapping("/search-category")
  public BaseResponse<Page<GetErrorBoardResponse>> readCategory(
          @RequestParam Long workspaceId,
      @RequestParam int page,
      @RequestParam int size,
      @RequestParam String category) throws BaseException {
    Page<GetErrorBoardResponse> response = errBoardService.readCategory(workspaceId,category, page, size);
    return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
  }
}