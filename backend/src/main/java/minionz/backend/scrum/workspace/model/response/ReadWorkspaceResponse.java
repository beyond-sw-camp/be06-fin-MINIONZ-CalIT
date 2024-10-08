package minionz.backend.scrum.workspace.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadWorkspaceResponse {
    private Long workspaceId;
    private String workspaceName;
    private int avatar;
}
