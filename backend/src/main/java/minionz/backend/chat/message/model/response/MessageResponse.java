package minionz.backend.chat.message.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.common.BaseEntity;

import java.util.List;

@Builder
@Getter
public class MessageResponse extends BaseEntity {
    private String chatRoomName;
    private List<Long> participants;
    private String topicName;
    private String fileType;
    private String fileSize;
    private String fileName;
    private String fileUrl;

}
