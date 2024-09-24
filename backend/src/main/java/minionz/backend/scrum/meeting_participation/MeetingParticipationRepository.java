package minionz.backend.scrum.meeting_participation;

import minionz.backend.note.model.Note;
import minionz.backend.scrum.meeting.model.Meeting;
import minionz.backend.scrum.meeting_participation.model.MeetingParticipation;
import minionz.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingParticipationRepository extends JpaRepository<MeetingParticipation, Long> {
    MeetingParticipation findByUser_UserId(Long userId); // userId 필드 사용
}

