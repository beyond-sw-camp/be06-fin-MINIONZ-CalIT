package minionz.apiserver.board.qa_board;

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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qaboard")
public class QaBoardController {
  private final QaBoardService qaBoardService;
  private final CloudFileUpload cloudFileUpload;

  // 게시글 생성
  @PostMapping("/write")
  public BaseResponse<CreateQaBoardResponse> createQaBoard(
      @RequestParam Long workspaceId,
      @RequestPart(name = "request") CreateQaBoardRequest request,
      @RequestPart(name = "files") MultipartFile[] files,
      @AuthenticationPrincipal CustomSecurityUserDetails userDetails) throws BaseException {
    List<String> fileNames = cloudFileUpload.multipleUpload(files);
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
