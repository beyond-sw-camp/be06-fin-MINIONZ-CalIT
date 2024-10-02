package minionz.backend.scrum.workspace.model.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateWorkspaceRequest {
    public String workspaceName;
    public List<Long> participants;
}
