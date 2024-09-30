package minionz.backend.search;

import lombok.RequiredArgsConstructor;
import minionz.backend.search.model.request.WorkspaceUserRequest;
import minionz.backend.search.model.response.SearchUserResponse;
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

    public List<SearchUserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        return users.stream()
                .map(user -> searchUserResponse.builder()
                        .searchUserIdx(user.getUserId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .persona(user.getPersona())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SearchUserResponse> getUsernamesByWorkspaceId(Long workspaceId) {
        List<User> users = searchRepository.findByWorkspaceParticipations_Workspace_WorkspaceId(workspaceId);
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        return users.stream()
                .map(user -> searchUserResponse.builder()
                        .searchUserIdx(user.getUserId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SearchUserResponse> containedUser(String username) {
        List<User> users = searchRepository.findByUserNameContaining(username);
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        return users.stream()
                .map(user -> searchUserResponse.builder()
                        .searchUserIdx(user.getUserId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}