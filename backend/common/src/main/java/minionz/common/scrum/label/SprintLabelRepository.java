package minionz.common.scrum.label;

import minionz.common.scrum.label.model.SprintLabel;
import minionz.common.scrum.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintLabelRepository extends JpaRepository<SprintLabel, Long> {
    SprintLabel findByLabelNameAndWorkspace(String labelName, Workspace workspace);
    List<SprintLabel> findByWorkspaceWorkspaceId(Long workspaceId);
}
