package minionz.common.scrum.label;


import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.workspace.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
    TaskLabel findByLabelNameAndWorkspace(String labelName, Workspace workspace);
    List<TaskLabel> findByWorkspaceWorkspaceId(Long workspaceId);
}
