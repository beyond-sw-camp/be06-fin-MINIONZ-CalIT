package minionz.backend.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyProgressResponse {
    private int workspaceCount;
    private int allTaskCount;
    private int successTaskCount;
}
