package minionz.apiserver.chat.chat_room.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileInfoResponse {
    private String fileName;
    private String fileUrl;
    private String  fileSize;
    private String fileType;
}

