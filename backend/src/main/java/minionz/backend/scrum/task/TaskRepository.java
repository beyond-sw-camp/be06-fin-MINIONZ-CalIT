package minionz.backend.scrum.task;

import minionz.backend.scrum.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
   @Query("SELECT COUNT(*) AS task_count " +
   "FROM Task t " +
    "WHERE t.sprint.sprintId = :sprintId")
    int findTaskCount(Long sprintId);
}
