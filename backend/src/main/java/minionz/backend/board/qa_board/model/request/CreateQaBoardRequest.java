package minionz.backend.board.qa_board.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQaBoardRequest {
    private Long taskId;
    private String qaboardTitle;
    private String qaboardContent;

}
