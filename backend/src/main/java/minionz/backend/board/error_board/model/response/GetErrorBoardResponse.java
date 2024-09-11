package minionz.backend.board.error_board.model.response;

import lombok.*;
import minionz.backend.scrum.workspace.model.Workspace;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetErrorBoardResponse {
    private Long errorBoardId;
    private String errboardTitle;
    private String errboardContent;
    private String errboardCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long workspaceId;
    private List<GetErrorBoardImageResponse> getErrorBoardImageResponsesList;
}
