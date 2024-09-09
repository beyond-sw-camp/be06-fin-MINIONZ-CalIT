package minionz.backend.chat.message;

import minionz.backend.chat.message.model.Message;
import minionz.backend.chat.message.model.MessageStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT COUNT(m) FROM Message m " +
            "JOIN m.chatParticipation cp " +
            "JOIN cp.chatRoom cr " +
            "JOIN cp.user u " +
            "WHERE cr.chatRoomId = :chatRoomId " +
            "AND u.userId = :userId " +
            "AND m.messageStatus = :status")
    Long countUnreadMessagesByChatRoomIdAndUserId(Long chatRoomId,
                                                  Long userId,
                                                  MessageStatus status);

    @Query("SELECT m FROM Message m WHERE m.chatParticipation.chatRoom.chatRoomId = :chatRoomId ORDER BY m.createdAt DESC")
    List<Message> findLatestMessagesByChatRoomId(Long chatRoomId);





}

