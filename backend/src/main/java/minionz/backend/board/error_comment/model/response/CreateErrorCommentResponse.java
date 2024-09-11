package minionz.backend.board.error_comment.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateErrorCommentResponse {

    private Long errorCommentId;
    private String errCommentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<GetErrorCommentImageResponse> images;
}
