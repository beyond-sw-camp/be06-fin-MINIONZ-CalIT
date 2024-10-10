package minionz.apiserver.scrum.meeting_participation;

import minionz.common.scrum.meeting_participation.model.MeetingParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingParticipationRepository extends JpaRepository<MeetingParticipation, Long> {
    MeetingParticipation findByUser_UserId(Long userId); // userId 필드 사용
}

