package minionz.backend.scrum.sprint;

import minionz.backend.scrum.sprint.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findAllByWorkspaceWorkspaceId(Long id);
}
