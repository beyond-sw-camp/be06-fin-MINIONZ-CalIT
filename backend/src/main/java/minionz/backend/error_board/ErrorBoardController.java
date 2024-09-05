package minionz.backend.error_board;


import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.error_board.model.request.CreateErrorBoardRequest;
import minionz.backend.error_board.model.response.CreateErrorBoardResponse;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/errboard")
public class ErrorBoardController {
    private final ErrorBoardService errBoardService;
    private final CloudFileUpload cloudFileUpload;

    // 게시글 생성
    @PostMapping("/write")
    public ResponseEntity<BaseResponse> register(
            @RequestPart(name = "dto") CreateErrorBoardRequest dto,
            @RequestPart(name = "files") MultipartFile[] files) throws BaseException {
        List<String> fileNames = cloudFileUpload.multipleUpload(files);
        CreateErrorBoardResponse response = errBoardService.create(fileNames, dto);
        return ResponseEntity.ok(new BaseResponse(BaseResponseStatus.ERRORBOARD_CREATE_SUCCESS, response));
    }
}

