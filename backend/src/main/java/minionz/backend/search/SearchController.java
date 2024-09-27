package minionz.backend.search;

import lombok.RequiredArgsConstructor;
import minionz.backend.search.model.request.WorkspaceUserRequest;
import minionz.backend.search.model.request.SearchUserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    @PostMapping("/alluser")
    public List<SearchUserRequest> searchAllUser() {
        return searchService.getAllUser();
    }

    @PostMapping("/workspaceuser")
    public List<WorkspaceUserRequest> searchWorkspaceUser(@RequestBody WorkspaceUserRequest workspace) {
        return searchService.getUsernamesByWorkspaceId(workspace.getWorkspaceId());
    }
}