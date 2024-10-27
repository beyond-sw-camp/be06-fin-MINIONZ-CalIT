package minionz.apiserver.board.error_comment;

import minionz.apiserver.board.error_comment.model.request.CreateErrorCommentRequest;
import minionz.apiserver.board.error_comment.model.response.CreateErrorCommentResponse;
import minionz.apiserver.board.error_comment.model.response.GetErrorCommentImageResponse;
import minionz.apiserver.board.error_comment.model.response.GetErrorCommentResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.utils.CloudFileUpload;
import minionz.common.board.error_board.ErrorBoardRepository;
import minionz.common.board.error_board.model.ErrorBoard;
import minionz.common.board.error_comment.ErrorCommentImageRepository;
import minionz.common.board.error_comment.ErrorCommentRepository;
import minionz.common.board.error_comment.model.ErrorComment;
import minionz.common.board.error_comment.model.ErrorCommentImage;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
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
    private final UserRepository userRepository;

    public ErrorCommentService(ErrorCommentRepository errorCommnetRepository, ErrorCommentImageRepository errorCommentImageRepository, ErrorBoardRepository errorBoardRepository, CloudFileUpload cloudFileUpload,UserRepository userRepository){
        this.errorBoardRepository = errorBoardRepository;
        this.errorCommentImageRepository = errorCommentImageRepository;
        this.cloudFileUpload = cloudFileUpload;
        this.errorCommentRepository = errorCommnetRepository;
        this.userRepository = userRepository;
    }

    public CreateErrorCommentResponse create(Long errorBoardId, CreateErrorCommentRequest request, List<String> fileNames,Long userId) throws BaseException {

        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND)); // 예외 처리

        ErrorBoard errorBoard = errorBoardRepository.findById(errorBoardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ERRORBOARD_SERACH_FAIL));


        ErrorComment errorComment = ErrorComment.builder()
                .errCommentContent(request.getErrCommentContent())
                .errorBoard(errorBoard)
                .user(user1)
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
                .userName(errorComment.getUser().getUserName())
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
                            .userName(comment.getUser().getUserName())
                            .personaImage(comment.getUser().getPersona())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
