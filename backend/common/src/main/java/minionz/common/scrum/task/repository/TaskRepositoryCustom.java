package minionz.common.scrum.task.repository;

import minionz.common.scrum.task.model.Task;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepositoryCustom {
    int findTaskCount(Long sprintId);
    List<Task> findMyTask(Long userId);
    List<Task> findPriorityMyTasks(Long userId, Pageable pageable);
    int findAllTaskCount(Long workspaceId);
    int findSuccessTaskCount(Long workspaceId);
    int findMyTaskCount(Long userId, boolean done);
    List<Task> findUpcomingWorkspaceTasks(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    List<Task> findUpcomingMyTasks(Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    List<Task> findAllBySprintIdAndUserId(Long sprintId, Long userId);
    List<Task> findAllByWorkspaceId(Long workspaceId);
}