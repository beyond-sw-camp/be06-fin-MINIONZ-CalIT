package minionz.apiserver.chat.chat_participation;

import minionz.common.chat.chat_participation.model.ChatParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatParticipationRepository extends JpaRepository<ChatParticipation, Long> {
    List<ChatParticipation> findByUser_UserId(Long userId);
    List<ChatParticipation> findByUser_UserIdAndIsActiveTrue(Long userId);
    List<ChatParticipation> findAllByUser_UserId(Long userId);
    ChatParticipation findByChatRoom_ChatRoomIdAndUser_UserId(Long chatRoomId, Long userId);
    boolean existsByChatRoom_ChatRoomIdAndUser_UserId(Long chatRoomId, Long userId);

    @Query("SELECT cp FROM ChatParticipation cp WHERE cp.chatRoom.chatRoomId = :chatRoomId AND cp.user.userId = :userId")
    ChatParticipation findByChatRoom_ChatRoomIdAndUser_UserId2(Long chatRoomId, Long userId);
}

