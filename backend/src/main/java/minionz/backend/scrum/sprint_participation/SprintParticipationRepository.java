package minionz.backend.scrum.sprint_participation;

import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintParticipationRepository extends JpaRepository<SprintParticipation, Long> {
}
