package minionz.apiserver.scrum.task_participation;

import minionz.common.scrum.task_participation.model.TaskParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskParticipationRepository extends JpaRepository<TaskParticipation, Long> {
}
