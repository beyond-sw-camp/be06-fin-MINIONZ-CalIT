package minionz.backend.scrum.task;

import minionz.backend.scrum.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
   @Query("SELECT COUNT(*) AS task_count " +
           "FROM Task t " +
           "WHERE t.sprint.sprintId = :sprintId")
    int findTaskCount(Long sprintId);

   List<Task> findAllBySprintSprintId(Long sprintId);

   @Query("SELECT t FROM Task t " +
           "JOIN FETCH TaskParticipation tp ON t = tp.task " +
           "JOIN FETCH Sprint s ON t.sprint = s " +
           "WHERE tp.user.userId = :userId AND s.endDate > CURRENT_TIMESTAMP")
   List<Task> findMyTask(Long userId);
}


