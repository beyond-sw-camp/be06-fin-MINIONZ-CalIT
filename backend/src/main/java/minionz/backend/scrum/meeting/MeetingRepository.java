package minionz.backend.scrum.meeting;

import minionz.backend.scrum.meeting.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
