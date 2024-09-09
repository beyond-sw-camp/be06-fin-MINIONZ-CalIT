package minionz.backend.board.error_board.model.request;

import lombok.*;

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
