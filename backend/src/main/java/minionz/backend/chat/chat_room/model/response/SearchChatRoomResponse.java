package minionz.backend.chat.chat_room.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SearchChatRoomResponse {
    private Long chatroomId;
    private String chatRoomName;
    private String messageContents;
    private LocalDateTime createdAt;
    private Integer UnreadMessages;
}
