package minionz.backend.scrum.workspace.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadWorkspaceResponse {
    private Long workspaceId;
    private String workspaceName;
}
