package minionz.backend.board.error_comment.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateErrorCommentRequest {
    private String errCommentContent;

}
