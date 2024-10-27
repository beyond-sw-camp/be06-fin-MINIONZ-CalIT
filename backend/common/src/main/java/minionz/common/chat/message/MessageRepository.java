package minionz.common.chat.message;

import minionz.common.chat.message.model.Message;
import minionz.common.chat.message.model.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    @Query("SELECT m FROM Message m " +
            "JOIN m.chatParticipation cp " +
            "JOIN cp.chatRoom cr " +
            "WHERE cr.chatRoomId = :chatRoomId " +
            "ORDER BY m.createdAt DESC")
    List<Message> findLatestMessagesByChatRoomId(Long chatRoomId);

    @Query("SELECT m FROM Message m WHERE m.chatParticipation.chatRoom.chatRoomId = :chatRoomId AND m.deletedAt IS NULL ORDER BY m.createdAt DESC")
    Page<Message> findByChatRoomIdAndDeletedAtIsNull(Long chatRoomId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.messageId = :messageId")
    Message findMessageById(Long messageId);

    @Query("SELECT m FROM Message m WHERE m.chatParticipation.chatRoom.chatRoomId = :chatRoomId AND m.messageType = 'FILE' AND m.deletedAt IS NULL")
    List<Message> findFilesByChatRoomId(Long chatRoomId);

    @Query("SELECT m FROM Message m " +
            "WHERE m.chatRoomId = :chatRoomId " +
            "AND m.messageStatus = :status")
    List<Message> findUnreadMessagesByChatRoomId(Long chatRoomId, MessageStatus status);




}

