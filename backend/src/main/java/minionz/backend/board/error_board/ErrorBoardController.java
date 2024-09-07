package minionz.backend.board.error_board;

import lombok.RequiredArgsConstructor;
import minionz.backend.board.error_board.model.request.CreateErrorBoardRequest;
import minionz.backend.board.error_board.model.response.CreateErrorBoardResponse;
import minionz.backend.board.error_board.model.response.GetErrorBoardResponse;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestPart(name = "request") CreateErrorBoardRequest request,
            @RequestPart(name = "files") MultipartFile[] files) throws BaseException {
        List<String> fileNames = cloudFileUpload.multipleUpload(files);
        CreateErrorBoardResponse response = errBoardService.create(fileNames, request);
        return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_CREATE_SUCCESS, response);
    }

    //게시글 하나 조회
    @GetMapping("/search")
    public BaseResponse<GetErrorBoardResponse>search(
            @RequestParam Long boardId) throws BaseException {
        GetErrorBoardResponse response = errBoardService.read(boardId);
        return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
    }


    //게시글 전체 조회
    @GetMapping("/search-all")
    public BaseResponse<Page<GetErrorBoardResponse>> readAllErrorBoard(
            @RequestParam int page,
            @RequestParam int size) throws BaseException {
        Page<GetErrorBoardResponse> response = errBoardService.readAll(page, size);
        return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
    }
    //단어별 조회
    @GetMapping("/search-keyword")
    public BaseResponse<Page<GetErrorBoardResponse>> readKeyword(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String keyword) throws BaseException {
        Page<GetErrorBoardResponse> response = errBoardService.readKeyword(keyword, page, size);
        return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
    }
    //카테고리별 조회
    @GetMapping("/search-category")
    public BaseResponse<Page<GetErrorBoardResponse>> readCategory(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String category) throws BaseException {
        Page<GetErrorBoardResponse> response = errBoardService.readCategory(category, page, size);
        return new BaseResponse<>(BaseResponseStatus.ERRORBOARD_SEARCH_SUCCESS, response);
    }






}

