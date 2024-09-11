package minionz.backend.chat.chat_room.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateChatRoomResponse {

    private Long chatRoomId;
    private String chatRoomName;
    private List<Long> participants;
    private String topic;
    private String creationMessage;

}
