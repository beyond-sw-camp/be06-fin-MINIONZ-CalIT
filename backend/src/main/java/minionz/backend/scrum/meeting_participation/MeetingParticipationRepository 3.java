package minionz.backend.scrum.meeting_participation;

import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingParticipationRepository extends JpaRepository<MeetingParticipation, Long> {
}
