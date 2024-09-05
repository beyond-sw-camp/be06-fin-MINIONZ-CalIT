package minionz.backend.chat.chat_room.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.common.BaseEntity;

import java.util.List;

@Builder
@Getter
public class CreateChatRoomResponse extends BaseEntity {

    private Long chatRoomId;
    private String chatRoomName;
    private List<Long> participants;
    private String topic;

}
