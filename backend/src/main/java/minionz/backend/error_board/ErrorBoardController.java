package minionz.backend.error_board;


import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.error_board.model.request.CreateErrorBoardRequest;
import minionz.backend.error_board.model.response.CreateErrorBoardResponse;
import minionz.backend.error_board.model.response.GetErrorBoardImageResponse;
import minionz.backend.error_board.model.response.GetErrorBoardResponse;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/errboard")
public class ErrorBoardController {

    private final CloudFileUpload cloudFileUpload;
    private final ErrorBoardService errorBoardService;
    // 게시글 생성
    @PostMapping("/write")
    public ResponseEntity<BaseResponse> register(
            @RequestPart(name = "dto") CreateErrorBoardRequest dto,
            @RequestPart(name = "files") MultipartFile[] files) throws BaseException {
        List<String> fileNames = cloudFileUpload.multipleUpload(files);
        CreateErrorBoardResponse response = errorBoardService.create(fileNames, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.ERRORBOARD_CREATE_SUCCESS, response));
    }
    //게시글 전체 조회
    @GetMapping("/search-all")
    public ResponseEntity<BaseResponse<Page<GetErrorBoardResponse>>> searchAll(
            @RequestParam int page,
            @RequestParam int size) throws BaseException {
        Page<GetErrorBoardResponse> response = errorBoardService.readAll(page, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response));
    }
    //게시글 카테고리 별 조회
    @GetMapping("/search-category")
    public ResponseEntity<BaseResponse<List<GetErrorBoardResponse>>> searchCategory(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String category) throws BaseException {
        Page<GetErrorBoardResponse> response = errorBoardService.searchKeyword(category, page, size);
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response));
    }


}

