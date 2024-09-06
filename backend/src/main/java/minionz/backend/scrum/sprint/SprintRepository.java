package minionz.backend.scrum.sprint;

import minionz.backend.scrum.sprint.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
