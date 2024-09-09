package minionz.backend.chat.chat_participation;

import minionz.backend.chat.chat_participation.model.ChatParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long> {
    List<ChatParticipation> findByUser_UserId(Long userId);
    Optional<ChatParticipation> findByChatRoom_ChatRoomIdAndUser_UserId(Long chatRoomId, Long userId);
}

