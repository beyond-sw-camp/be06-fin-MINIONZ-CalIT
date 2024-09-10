package minionz.backend.scrum.meeting;

import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.sprint.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m " +
            "JOIN FETCH m.sprint s " +
            "JOIN FETCH s.workspace w " +
            "WHERE w.workspaceId = :workspaceId " +
            "AND m.startDate <= :endDate " +
            "AND m.startDate >= :startDate")
    List<Meeting> findMeetingsInMonth(Long workspaceId, LocalDateTime startDate, LocalDateTime endDate);
}
