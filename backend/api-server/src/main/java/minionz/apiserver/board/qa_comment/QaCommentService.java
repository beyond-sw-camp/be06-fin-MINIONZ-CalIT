package minionz.apiserver.board.qa_comment;


import minionz.apiserver.board.qa_board.QaBoardRepository;
import minionz.common.board.qa_board.model.QaBoard;
import minionz.common.board.qa_comment.model.ProgressStatus;
import minionz.common.board.qa_comment.model.QaComment;
import minionz.common.board.qa_comment.model.QaCommentImage;
import minionz.apiserver.board.qa_comment.model.request.CreateQaCommentRequest;
import minionz.apiserver.board.qa_comment.model.response.CreateQaCommentResponse;
import minionz.apiserver.board.qa_comment.model.response.GetQaCommentImageResponse;
import minionz.apiserver.board.qa_comment.model.response.GetQaCommentResponse;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.utils.CloudFileUpload;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QaCommentService {

    private final QaCommentRepository qaCommentRepository;
    private final QaCommentImageRepository qaCommentImageRepository;
    private final QaBoardRepository qaBoardRepository;
    private final CloudFileUpload cloudFileUpload;
    private final UserRepository userRepository;
    public QaCommentService(QaCommentRepository qaCommentRepository,QaCommentImageRepository qaCommentImageRepository,CloudFileUpload cloudFileUpload, QaBoardRepository qaBoardRepository,UserRepository userRepository){
        this.qaCommentRepository = qaCommentRepository;
        this.qaCommentImageRepository = qaCommentImageRepository;
        this.cloudFileUpload = cloudFileUpload;
        this.qaBoardRepository = qaBoardRepository;
        this.userRepository = userRepository;
    }

    public CreateQaCommentResponse create(Long qaBoardId, CreateQaCommentRequest request, List<String> fileNames,Long userId) throws BaseException {

        // Optional 처리
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND)); // 예외 처리

        ProgressStatus progressStatus = ProgressStatus.valueOf(request.getProgressStatus().toUpperCase());
        QaBoard qaBoard = qaBoardRepository.findById(qaBoardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.QABOARD_SERACH_FAIL));

        QaComment qaComment = QaComment.builder()
                .qaCommentTitle(request.getQaCommentTitle())
                .qaCommentContent(request.getQaCommentContent())
                .qaBoard(qaBoard)
                .progressStatus(progressStatus)
                .user(user1)
                .build();
        qaCommentRepository.save(qaComment);


        List<GetQaCommentImageResponse> imageResponses = new ArrayList<>();

        for (String fileName : fileNames) {
            QaCommentImage qaCommentImage = QaCommentImage.builder()
                    .imageUrl(fileName)
                    .qaComment(qaComment)
                    .build();
            qaCommentImageRepository.save(qaCommentImage);

            imageResponses.add(GetQaCommentImageResponse.builder()
                    .qaCommentImageId(qaCommentImage.getQaCommentImageId())
                            .imageUrl(qaCommentImage.getImageUrl())
                            .createdAt(qaCommentImage.getCreatedAt())
                            .modifiedAt(qaCommentImage.getModifiedAt())

                    .build());
        }

        return CreateQaCommentResponse.builder()
                .userName(qaComment.getUser().getUserName())
                .qaCommentId(qaComment.getQaCommentId())
                .qaCommentTitle(qaComment.getQaCommentTitle())
                .qaCommentContent(qaComment.getQaCommentContent())
                .createdAt(qaComment.getCreatedAt())
                .modifiedAt(qaComment.getModifiedAt())
                .images(imageResponses)
                .progressStatus(String.valueOf(qaComment.getProgressStatus()))
                .build();
    }

    public List<GetQaCommentResponse> read(Long qaBoardId) throws BaseException {
        QaBoard qaBoard = qaBoardRepository.findById(qaBoardId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.QABOARD_SERACH_FAIL));

        List<QaComment> comments = qaCommentRepository.findByQaBoard(qaBoard);

        return comments.stream()
                .map(comment -> {
                    List<QaCommentImage> commentImages = qaCommentImageRepository.findByQaComment(comment);
                    List<GetQaCommentImageResponse> imageResponses = commentImages.stream()
                            .map(image -> GetQaCommentImageResponse.builder()
                                    .qaCommentImageId(image.getQaCommentImageId())
                                    .imageUrl(image.getImageUrl())
                                    .createdAt(image.getCreatedAt())
                                    .modifiedAt(image.getModifiedAt())
                                    .build())
                            .collect(Collectors.toList());

                    return GetQaCommentResponse.builder()
                            .userName(comment.getUser().getUserName())
                            .qaCommentId(comment.getQaCommentId())
                            .qaCommentTitle(comment.getQaCommentTitle())
                            .qaCommentContent(comment.getQaCommentContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .images(imageResponses)
                            .progressStatus(String.valueOf(comment.getProgressStatus()))
                            .personaImage(comment.getUser().getPersona())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
