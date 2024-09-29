package minionz.backend.board.qa_comment.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQaCommentRequest {
    private String qaCommentTitle;
    private String qaCommentContent;
    private String progressStatus;


}