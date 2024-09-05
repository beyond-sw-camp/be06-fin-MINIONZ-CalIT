package minionz.backend.scrum.workspace;

import minionz.backend.scrum.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query("SELECT w FROM Workspace w " +
            "JOIN FETCH w.workspaceParticipations wp " +
            "WHERE wp.user.userId = :userId " +
            "AND wp.isValid = true")
    List<Workspace> findWorkspaceByUserId(Long userId);
}
