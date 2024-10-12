package minionz.apiserver.board.qa_board.model.response;


import lombok.*;
import minionz.common.board.qa_board.model.AnswerStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQaBoardResponse {
    private Long qaBoardId;
    private String qaboardTitle;
    private String qaboardContent;
    private AnswerStatus answerStatus; //답변상태
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<GetQaBoardImageResponse> getQaBoardImageResponseList;
    private Long workspaceId;

    private String userName;
    private String taskName;
}
