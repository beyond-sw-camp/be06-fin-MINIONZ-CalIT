package minionz.backend.board.error_comment;

import minionz.backend.board.error_comment.model.ErrorComment;
import minionz.backend.board.error_comment.model.ErrorCommentImage;
import minionz.backend.board.error_comment.model.request.CreateErrorCommentRequest;
import minionz.backend.board.error_comment.model.response.CreateErrorCommentResponse;
import minionz.backend.board.error_comment.model.response.GetErrorCommentImageResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorCommentService {

    private final ErrorCommnetRepository errorCommnetRepository;
    private final ErrorCommentImageRepository errorCommentImageRepository;
    public ErrorCommentService(ErrorCommnetRepository errorCommnetRepository,ErrorCommentImageRepository errorCommentImageRepository) {
        this.errorCommentImageRepository = errorCommentImageRepository;
        this.errorCommnetRepository = errorCommnetRepository;

    }




}
