package minionz.apiserver.chat.chat_room.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReadChatRoomResponse {
    private Long chatroomId;
    private Long workspaceId;
    private String chatRoomName;
    private String messageContents;
    private LocalDateTime createdAt;
    private Integer UnreadMessages;

}
