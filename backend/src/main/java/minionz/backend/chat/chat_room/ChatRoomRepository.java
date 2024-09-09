package minionz.backend.chat.chat_room;

import minionz.backend.chat.chat_room.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
