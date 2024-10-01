package minionz.backend.search;

import lombok.RequiredArgsConstructor;
import minionz.backend.search.model.response.SearchUserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
  private final SearchService searchService;

  @GetMapping("/alluser")
  public List<SearchUserResponse> searchAllUser() {
    return searchService.getAllUser();
  }

  @GetMapping("/workspaceuser")
  public List<SearchUserResponse> searchWorkspaceUser(@RequestParam Long workspaceId) {
    return searchService.getUsernamesByWorkspaceId(workspaceId);
  }

  @GetMapping("/containeduser")
  public List<SearchUserResponse> searchWorkspaceUser(@RequestParam String username) {
    return searchService.containedUser(username);
  }
}