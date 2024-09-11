package minionz.backend.scrum.label;


import minionz.backend.scrum.label.model.TaskLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
    TaskLabel findByLabelName(String labelName);
    List<TaskLabel> findByWorkspaceWorkspaceId(Long workspaceId);
}
