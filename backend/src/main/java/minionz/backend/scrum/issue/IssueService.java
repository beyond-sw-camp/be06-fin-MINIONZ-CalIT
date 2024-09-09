package minionz.backend.scrum.issue;

import lombok.RequiredArgsConstructor;
import minionz.backend.scrum.issue.model.Issue;
import minionz.backend.scrum.issue.model.request.CreateIssueRequest;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;

    public void createIssue(Long workspaceId, CreateIssueRequest request) {
        issueRepository.save(
                Issue
                        .builder()
                        .issueTitle(request.getTitle())
                        .issueContents(request.getContents())
                        .status(true)
                        .workspace(Workspace.builder().workspaceId(workspaceId).build())
                        .user(User.builder().userId(request.getManagerId()).build())
                        .build()
        );
    }
}
