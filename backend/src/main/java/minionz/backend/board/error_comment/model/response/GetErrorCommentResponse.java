package minionz.backend.board.error_comment.model.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetErrorCommentResponse {

    private Integer personaImage;
    private String userName;
    private Long ErrorCommentId;
    private String ErrorCommentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<GetErrorCommentImageResponse> images;
}
