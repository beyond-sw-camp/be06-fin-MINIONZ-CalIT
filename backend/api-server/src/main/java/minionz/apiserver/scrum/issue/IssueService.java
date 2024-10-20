package minionz.apiserver.scrum.issue;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.issue.model.request.CreateIssueRequest;
import minionz.apiserver.scrum.issue.model.response.ReadIssueResponse;
import minionz.common.scrum.issue.repository.IssueRepository;
import minionz.common.scrum.issue.model.Issue;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    public void createIssue(Long workspaceId, CreateIssueRequest request) {
        issueRepository.save(
                Issue.builder()
                        .issueTitle(request.getTitle())
                        .issueContents(request.getContents())
                        .status(true)
                        .startDate(request.getStartDate())
                        .endDate(request.getEndDate())
                        .workspace(Workspace.builder().workspaceId(workspaceId).build())
                        .manager(User.builder().userId(request.getManagerId()).build())
                        .build()
        );
    }

    // 개별 이슈 조회
    public ReadIssueResponse detailIssue(Long workspaceId, Long issueId) throws BaseException{
        // 이슈 존재 확인
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.ISSUE_NOT_FOUND));

        // 이슈와 워크스페이스 일치
        if (!issue.getWorkspace().getWorkspaceId().equals(workspaceId)) {
            throw new BaseException(BaseResponseStatus.ISSUE_WORKSPACE_MISMATCH);
        }

        return ReadIssueResponse.builder()
                .title(issue.getIssueTitle())
                .contents(issue.getIssueContents())
                .startDate(issue.getStartDate())
                .endDate(issue.getEndDate())
                .status(issue.isStatus())
                .manager(issue.getManager().getUserName())
                .build();
    }

    // 리스트 조회
    public List<ReadIssueResponse> listIssues(Long workspaceId) {
        List<Issue> issues = issueRepository.findByWorkspace_WorkspaceId(workspaceId);

        return issues.stream().map(issue -> ReadIssueResponse.builder()
                        .title(issue.getIssueTitle())
                        .contents(issue.getIssueContents())
                        .startDate(issue.getStartDate())
                        .endDate(issue.getEndDate())
                        .status(issue.isStatus())
                        .manager(issue.getManager().getUserName())
                        .build())
                .collect(Collectors.toList());
    }
}

