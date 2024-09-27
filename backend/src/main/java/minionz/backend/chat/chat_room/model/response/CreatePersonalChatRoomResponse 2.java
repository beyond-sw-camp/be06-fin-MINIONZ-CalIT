package minionz.backend.chat.chat_room.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePersonalChatRoomResponse {
    private Long chatRoomId;
    private String chatRoomName;
    private Long senderId;
    private Long receiverId;
    private String topic;
}
