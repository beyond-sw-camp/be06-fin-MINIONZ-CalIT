package minionz.common.scrum.task;

import minionz.common.scrum.task.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT COUNT(*) AS task_count " +
            "FROM Task t " +
            "WHERE t.sprint.sprintId = :sprintId")
    int findTaskCount(Long sprintId);

    List<Task> findAllBySprintSprintId(Long sprintId);


    List<Task> findAllByWorkspaceWorkspaceId(Long workspaceId);

    @Query("SELECT t FROM Task t " +
            "JOIN FETCH TaskParticipation tp ON t = tp.task " +
            "JOIN FETCH Sprint s ON t.sprint = s " +
            "WHERE tp.user.userId = :userId AND s.endDate > CURRENT_TIMESTAMP")
    List<Task> findMyTask(Long userId);

    @Query("SELECT COUNT(*) AS mytask_count FROM Task t " +
            "JOIN FETCH TaskParticipation tp ON t = tp.task " +
            "JOIN FETCH Sprint s ON t.sprint = s " +
            "WHERE tp.user.userId = :userId AND s.endDate > CURRENT_TIMESTAMP")
    int findMyAllTask(Long userId);

    @Query("SELECT COUNT(*) AS mydonetask_count FROM Task t " +
            "JOIN FETCH TaskParticipation tp ON t = tp.task " +
            "JOIN FETCH Sprint s ON t.sprint = s " +
            "WHERE tp.user.userId = :userId AND s.endDate > CURRENT_TIMESTAMP AND t.status = minionz.common.scrum.task.model.TaskStatus.DONE ")
    int findMyDoneTask(Long userId);

    @Query("SELECT t FROM Task t " +
            "JOIN FETCH t.sprint s " +
            "JOIN s.workspace w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND t.startDate <= :endDate " +
            "AND t.endDate >= :startDate ORDER BY t.endDate ASC ")
    List<Task> findUpcomingWorkspaceTasks(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "JOIN FETCH TaskParticipation tp ON t = tp.task " +
            "JOIN FETCH t.sprint s " +
            "WHERE tp.user.userId = :userId " +
            "AND t.startDate <= :endDate " +
            "AND t.endDate >= :startDate ORDER BY t.endDate ASC ")
    List<Task> findUpcomingMyTasks(Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("SELECT t FROM Task t " +
            "JOIN FETCH TaskParticipation tp ON t = tp.task " +
            "JOIN FETCH t.sprint s " +
            "WHERE tp.user.userId = :userId " +
            "AND s.endDate > CURRENT_TIMESTAMP " +
            "ORDER BY t.endDate ASC, " +
            "CASE t.priority " +
            "WHEN minionz.common.scrum.task.model.TaskLevel.HIGH THEN 1 " +
            "WHEN minionz.common.scrum.task.model.TaskLevel.MED THEN 2 " +
            "WHEN minionz.common.scrum.task.model.TaskLevel.LOW THEN 3 " +
            "END ASC")
    List<Task> findPriorityMyTasks(Long userId, Pageable pageable);


    @Query("SELECT COUNT(t) FROM Task t " +
            "JOIN FETCH Sprint s ON t.sprint = s " +
            "JOIN FETCH Workspace w ON s.workspace = w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND s.endDate > CURRENT_TIMESTAMP ")
    int findAllTaskCount(Long workspaceId);

    @Query("SELECT COUNT(t) FROM Task t " +
            "JOIN FETCH Sprint s ON t.sprint = s " +
            "JOIN FETCH Workspace w ON s.workspace = w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND s.endDate > CURRENT_TIMESTAMP " +
            "AND t.status = minionz.common.scrum.task.model.TaskStatus.DONE")
    int findSuccessTaskCount(Long workspaceId);

    Page<Task> findTasksByEndDateAfter(LocalDateTime endDate, Pageable pageable);
}


