package minionz.backend.scrum.workspace_participation;

import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceParticipationRepository extends JpaRepository<WorkspaceParticipation, Long> {
    WorkspaceParticipation findWorkspaceParticipationByWorkspaceAndUserAndIsManager(Workspace workspace, User user, Boolean isManager);
}
