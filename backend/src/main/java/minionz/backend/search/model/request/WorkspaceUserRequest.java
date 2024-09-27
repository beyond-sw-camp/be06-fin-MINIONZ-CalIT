package minionz.backend.search.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkspaceUserRequest {
    Long workspaceId;
    String username;

}
