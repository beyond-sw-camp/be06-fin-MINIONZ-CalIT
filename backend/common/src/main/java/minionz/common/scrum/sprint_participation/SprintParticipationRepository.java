package minionz.common.scrum.sprint_participation;

import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.sprint_participation.model.SprintParticipation;
import minionz.common.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SprintParticipationRepository extends JpaRepository<SprintParticipation, Long> {
    SprintParticipation findBySprintAndIsManager(Sprint sprint, Boolean isManager);
    SprintParticipation findBySprintAndUser(Sprint sprint, User user);
}
