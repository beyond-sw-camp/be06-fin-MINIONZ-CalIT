package minionz.backend.search;

import minionz.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SearchRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findByUserNameContaining(String username);
    List<User> findByWorkspaceParticipations_Workspace_WorkspaceId(Long workspaceId);
}