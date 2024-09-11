package minionz.backend.scrum.issue;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponse;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.issue.model.request.CreateIssueRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @PostMapping("/{workspaceId}")
    public BaseResponse<BaseResponseStatus> createIssue(@PathVariable Long workspaceId, @RequestBody CreateIssueRequest request) {
        try {
            issueService.createIssue(workspaceId, request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.ISSUE_CREATE_SUCCESS);
    }

}
