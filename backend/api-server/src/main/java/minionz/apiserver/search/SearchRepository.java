package minionz.apiserver.search;

import minionz.common.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findByLoginIdContaining(String loginId);
    List<User> findByWorkspaceParticipations_Workspace_WorkspaceId(Long workspaceId);
}