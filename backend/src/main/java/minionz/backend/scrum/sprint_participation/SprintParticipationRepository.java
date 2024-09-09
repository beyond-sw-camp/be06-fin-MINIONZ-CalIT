package minionz.backend.scrum.sprint_participation;

import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SprintParticipationRepository extends JpaRepository<SprintParticipation, Long> {
    SprintParticipation findBySprintAndIsManager(Sprint sprint, Boolean isManager);
}
