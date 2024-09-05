package minionz.backend.scrum.workspace_participation;

import minionz.backend.scrum.workspace_participation.model.WorkspaceParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceParticipationRepository extends JpaRepository<WorkspaceParticipation,Long> {
}
