package minionz.common.scrum.sprint;

import minionz.common.scrum.sprint.model.Sprint;
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
    List<Sprint> findSprintsInPeriod(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT s FROM Sprint s " +
            "JOIN FETCH s.sprintParticipations sp " +
            "WHERE sp.user.userId = :userId " +
            "AND s.startDate <= :endDate " +
            "AND s.endDate >= :startDate")
    List<Sprint> findMySprintsInPeriod(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(s) FROM Sprint s " +
            "JOIN s.workspace w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND s.startDate <= :endDate " +
            "AND s.endDate >= :startDate")
    int findInprogressSprintCount(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(s) FROM Sprint s " +
            "JOIN s.workspace w " +
            "WHERE w.workspaceId = :workspaceId ")
    int findAllSprintCount(Long workspaceId);

    List<Sprint> findSprintsByEndDateAfter(LocalDateTime endDate);

}
