package minionz.backend.board.qa_board.model.response;


import lombok.*;
import minionz.backend.board.error_board.model.response.GetErrorBoardImageResponse;
import minionz.backend.board.qa_board.model.AnswerStatus;

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
}
