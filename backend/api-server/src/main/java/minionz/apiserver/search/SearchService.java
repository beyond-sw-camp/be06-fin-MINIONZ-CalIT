package minionz.apiserver.search;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.search.model.response.SearchUserResponse;
import minionz.common.user.UserRepository;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final UserRepository userRepository;

    public List<SearchUserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        return users.stream()
                .map(user -> SearchUserResponse.builder()
                        .searchUserIdx(user.getUserId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .persona(user.getPersona())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SearchUserResponse> getUsernamesByWorkspaceId(Long workspaceId) {
        List<User> users = userRepository.findByWorkspaceParticipations_Workspace_WorkspaceIdAndWorkspaceParticipations_IsValidTrue(workspaceId);
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        return users.stream()
                .map(user -> searchUserResponse.builder()
                        .persona(user.getPersona())
                        .searchUserIdx(user.getUserId())
                        .loginId(user.getLoginId())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .persona(user.getPersona())
                        .build())
                .collect(Collectors.toList());
    }

    public List<SearchUserResponse> containedUser(String loginId) {
        List<User> users = userRepository.findByLoginIdContaining(loginId);
        SearchUserResponse searchUserResponse = new SearchUserResponse();
        return users.stream()
                .map(user -> searchUserResponse.builder()
                        .searchUserIdx(user.getUserId())
                        .userName(user.getUserName())
                        .loginId(user.getLoginId())
                        .email(user.getEmail())
                        .persona(user.getPersona())
                        .build())
                .collect(Collectors.toList());
    }
}