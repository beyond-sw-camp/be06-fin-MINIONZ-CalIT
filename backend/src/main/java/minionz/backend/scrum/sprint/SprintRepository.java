package minionz.backend.scrum.sprint;

import minionz.backend.scrum.sprint.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findAllByWorkspaceWorkspaceId(Long id);

    @Query("SELECT s FROM Sprint s " +
            "JOIN s.workspace w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND s.startDate <= :endDate " +
            "AND s.endDate >= :startDate")
    List<Sprint> findSprintsInMonth(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate);

}
