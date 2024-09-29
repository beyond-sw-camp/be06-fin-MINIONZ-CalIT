package minionz.backend.chat.chat_room;

import minionz.backend.chat.chat_room.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c FROM ChatRoom c WHERE LOWER(c.chatRoomName) LIKE LOWER(CONCAT('%', :chatRoomName, '%'))")
    List<ChatRoom> findByChatRoomNameContainingIgnoreCase(String chatRoomName);
}
