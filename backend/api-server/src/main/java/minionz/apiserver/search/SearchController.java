package minionz.apiserver.search;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.search.model.response.SearchUserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
  private final SearchService searchService;

  @GetMapping("/alluser")
  public BaseResponse<List<SearchUserResponse>> searchAllUser() {
    List<SearchUserResponse> result = searchService.getAllUser();
    return new BaseResponse<>(BaseResponseStatus.SEARCH_USER_SUCCESS,result);
  }

  @GetMapping("/workspaceuser")
  public BaseResponse<List<SearchUserResponse>> searchWorkspaceUser(@RequestParam Long workspaceId) {
    List<SearchUserResponse> result = searchService.getUsernamesByWorkspaceId(workspaceId);
    return new BaseResponse<>(BaseResponseStatus.SEARCH_USER_SUCCESS,result);
  }

  @GetMapping("/containeduser")
  public BaseResponse<List<SearchUserResponse>> searchWorkspaceUser(@RequestParam String loginId) {
    List<SearchUserResponse> result = searchService.containedUser(loginId);
    return new BaseResponse<>(BaseResponseStatus.SEARCH_USER_SUCCESS,result);
  }
}