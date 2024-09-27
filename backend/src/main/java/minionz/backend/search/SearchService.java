package minionz.backend.search;

import lombok.RequiredArgsConstructor;
import minionz.backend.search.model.request.WorkspaceUserRequest;
import minionz.backend.user.UserRepository;
import minionz.backend.user.model.User;
import minionz.backend.search.model.request.SearchUserRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final SearchRepository searchRepository;
    private final UserRepository userRepository;

    public List<SearchUserRequest> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new SearchUserRequest(user.getUserName()))
                .collect(Collectors.toList());
    }

    public List<WorkspaceUserRequest> getUsernamesByWorkspaceId(Long workspaceId) {
        List<User> users = searchRepository.findByWorkspaceParticipations_Workspace_WorkspaceId(workspaceId);
        return users.stream()
                .map(user -> new WorkspaceUserRequest(workspaceId, user.getUserName())) // workspaceId를 포함
                .collect(Collectors.toList());
    }
}