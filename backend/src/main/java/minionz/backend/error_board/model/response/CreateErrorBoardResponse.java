package minionz.backend.error_board.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateErrorBoardResponse {

    private Long errorBoardId;
    private String errboardTitle;
    private String errboardContent;
    private String errboardCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<GetErrorBoardImageResponse> getErrorBoardImageResponsesList;

}
