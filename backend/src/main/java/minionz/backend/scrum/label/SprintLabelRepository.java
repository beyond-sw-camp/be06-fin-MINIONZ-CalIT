package minionz.backend.scrum.label;

import minionz.backend.scrum.label.model.SprintLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintLabelRepository extends JpaRepository<SprintLabel, Long> {
    SprintLabel findByLabelName(String labelName);
    List<SprintLabel> findByWorkspaceWorkspaceId(Long workspaceId);
}
