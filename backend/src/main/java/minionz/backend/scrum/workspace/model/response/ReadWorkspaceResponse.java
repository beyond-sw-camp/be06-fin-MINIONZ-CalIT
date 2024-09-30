package minionz.backend.scrum.workspace.model.response;

import lombok.*;

@Getter
@Builder
public class ReadWorkspaceResponse {
    private Long workspaceId;
    private String workspaceName;
    private int avatar;
}
