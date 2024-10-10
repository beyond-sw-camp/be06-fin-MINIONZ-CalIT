package minionz.apiserver.scrum.workspace_participation;

import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkspaceParticipationRepository extends JpaRepository<WorkspaceParticipation, Long> {
    WorkspaceParticipation findWorkspaceParticipationByWorkspaceAndUserAndIsManager(Workspace workspace, User user, Boolean isManager);
}
