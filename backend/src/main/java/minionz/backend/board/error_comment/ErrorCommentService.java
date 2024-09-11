package minionz.backend.board.error_comment;

import minionz.backend.board.error_board.ErrorBoardRepository;
import minionz.backend.board.error_board.model.ErrorBoard;
import minionz.backend.board.error_comment.model.ErrorComment;
import minionz.backend.board.error_comment.model.ErrorCommentImage;
import minionz.backend.board.error_comment.model.request.CreateErrorCommentRequest;
import minionz.backend.board.error_comment.model.response.CreateErrorCommentResponse;
import minionz.backend.board.error_comment.model.response.GetErrorCommentImageResponse;
import minionz.backend.board.error_comment.model.response.GetErrorCommentResponse;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.utils.CloudFileUpload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErrorCommentService {

    private final ErrorCommentRepository errorCommentRepository;
    private final ErrorCommentImageRepository errorCommentImageRepository;
    private final ErrorBoardRepository errorBoardRepository;
    private final CloudFileUpload cloudFileUpload;

    public ErrorCommentService(ErrorCommentRepository errorCommnetRepository, ErrorCommentImageRepository errorCommentImageRepository, ErrorBoardRepository errorBoardRepository, CloudFileUpload cloudFileUpload){
        this.errorBoardRepository = errorBoardRepository;
        this.errorCommentImageRepository = errorCommentImageRepository;
        this.cloudFileUpload = cloudFileUpload;
        this.errorCommentRepository = errorCommnetRepository;
    }

    public CreateErrorCommentResponse create(Long errorBoardId, CreateErrorCommentRequest request, List<String> fileNames) throws BaseException {

        ErrorBoard errorBoard = errorBoardRepository.findById(errorBoardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL));


        ErrorComment errorComment = ErrorComment.builder()
                .errCommentContent(request.getErrCommentContent())
                .errorBoard(errorBoard)
                .build();
        errorCommentRepository.save(errorComment);


        List<GetErrorCommentImageResponse> imageResponses = new ArrayList<>();

        for (String fileName : fileNames) {
            ErrorCommentImage commentImage = ErrorCommentImage.builder()
                    .imageUrl(fileName)
                    .errorComment(errorComment)
                    .build();
            errorCommentImageRepository.save(commentImage);

            imageResponses.add(GetErrorCommentImageResponse.builder()
                    .ErrorCommentImageId(commentImage.getErrorCommentImageId())
                    .imageUrl(commentImage.getImageUrl())
                    .createdAt(commentImage.getCreatedAt())
                    .modifiedAt(commentImage.getModifiedAt())
                    .build());
        }

        return CreateErrorCommentResponse.builder()
                .errorCommentId(errorComment.getErrorCommentId())
                .errCommentContent(errorComment.getErrCommentContent())
                .createdAt(errorComment.getCreatedAt())
                .modifiedAt(errorComment.getUpdatedAt())
                .images(imageResponses)
                .build();
    }


    public List<GetErrorCommentResponse> read(Long errorBoardId) throws BaseException {
        ErrorBoard errorBoard = errorBoardRepository.findById(errorBoardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL));

        List<ErrorComment> comments = errorCommentRepository.findByErrorBoard(errorBoard);

        return comments.stream()
                .map(comment -> {
                    List<ErrorCommentImage> commentImages = errorCommentImageRepository.findByErrorComment(comment);
                    List<GetErrorCommentImageResponse> imageResponses = commentImages.stream()
                            .map(image -> GetErrorCommentImageResponse.builder()
                                    .ErrorCommentImageId(image.getErrorCommentImageId())
                                    .imageUrl(image.getImageUrl())
                                    .createdAt(image.getCreatedAt())
                                    .modifiedAt(image.getModifiedAt())
                                    .build())
                            .collect(Collectors.toList());

                    return GetErrorCommentResponse.builder()
                            .ErrorCommentId(comment.getErrorCommentId())
                            .ErrorCommentContent(comment.getErrCommentContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getUpdatedAt())
                            .images(imageResponses)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
