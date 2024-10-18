package minionz.common.scrum.task.repository;

import minionz.common.scrum.task.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryCustom {
//    List<Task> findAllBySprintSprintId(Long sprintId);
    List<Task> findAllByWorkspaceWorkspaceId(Long workspaceId);
    Page<Task> findTasksByEndDateAfter(LocalDateTime endDate, Pageable pageable);

}