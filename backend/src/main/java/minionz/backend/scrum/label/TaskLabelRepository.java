package minionz.backend.scrum.label;


import minionz.backend.scrum.label.model.TaskLabel;
import minionz.backend.scrum.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
    TaskLabel findByLabelNameAndWorkspace(String labelName, Workspace workspace);
    List<TaskLabel> findByWorkspaceWorkspaceId(Long workspaceId);
}
