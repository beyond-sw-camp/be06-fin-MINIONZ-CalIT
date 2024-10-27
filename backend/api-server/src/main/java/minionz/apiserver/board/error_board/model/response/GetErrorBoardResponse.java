package minionz.apiserver.board.error_board.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetErrorBoardResponse {
    private String userName;
    private String taskName;
    private Integer personaImage;
    private Long errorBoardId;
    private String errboardTitle;
    private String errboardContent;
    private String errboardCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long workspaceId;
    private List<GetErrorBoardImageResponse> getErrorBoardImageResponsesList;

}
