package minionz.backend.board.qa_comment.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetQaCommentResponse {

    private Integer personaImage;
    private String userName;
    private Long qaCommentId;
    private String qaCommentTitle;
    private String qaCommentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String progressStatus;
    private List<GetQaCommentImageResponse> images;
}
