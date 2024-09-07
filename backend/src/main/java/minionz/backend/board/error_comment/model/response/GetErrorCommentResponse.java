package minionz.backend.board.error_comment.model.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetErrorCommentResponse {

    private Long ErrorCommentId;
    private String ErrorCommentContent;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
