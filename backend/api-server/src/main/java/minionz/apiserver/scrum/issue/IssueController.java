package minionz.apiserver.scrum.issue;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.issue.model.request.CreateIssueRequest;
import minionz.apiserver.scrum.issue.model.response.ReadIssueResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

  private final IssueService issueService;

  @PostMapping("/{workspaceId}")
  public BaseResponse<BaseResponseStatus> createIssue(@PathVariable Long workspaceId,
      @RequestBody CreateIssueRequest request) {
    try {
      issueService.createIssue(workspaceId, request);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }

    return new BaseResponse<>(BaseResponseStatus.ISSUE_CREATE_SUCCESS);
  }

  // 이슈 상세 조회
  @GetMapping("/{workspaceId}/detail/{issueId}")
  public BaseResponse<ReadIssueResponse> detailIssue(@PathVariable Long workspaceId, @PathVariable Long issueId) {
    try {
      ReadIssueResponse response = issueService.detailIssue(workspaceId, issueId);
      return new BaseResponse<>(BaseResponseStatus.ISSUE_RETRIEVAL_SUCCESS, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }
  }

  // 이슈 리스트 조회
  @GetMapping("/{workspaceId}/list")
  public BaseResponse<List<ReadIssueResponse>> listIssues(@PathVariable Long workspaceId) {
    try {
      List<ReadIssueResponse> response = issueService.listIssues(workspaceId);
      return new BaseResponse<>(BaseResponseStatus.ISSUE_LIST_SUCCESS, response);
    } catch (BaseException e) {
      return new BaseResponse<>(e.getStatus());
    }
  }
}
