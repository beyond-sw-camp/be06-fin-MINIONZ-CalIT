package minionz.backend.board.error_comment;

import lombok.RequiredArgsConstructor;
import minionz.backend.board.error_comment.model.request.CreateErrorCommentRequest;
import minionz.backend.board.error_comment.model.response.CreateErrorCommentResponse;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/errcomment")
public class ErrorCommentController {
    private final CloudFileUpload cloudFileUpload;
    private final ErrorCommentService errorCommentService;


}
