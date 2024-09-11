package minionz.backend.board.error_board.model.request;

import lombok.*;
import minionz.backend.scrum.task.model.Task;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateErrorBoardRequest {
    private String errboardTitle;
    private String errboardContent;
    private String category;

}
