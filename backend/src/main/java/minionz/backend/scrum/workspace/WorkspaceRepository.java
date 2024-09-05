package minionz.backend.scrum.workspace;

import minionz.backend.scrum.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
