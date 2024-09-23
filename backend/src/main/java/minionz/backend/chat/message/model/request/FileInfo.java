package minionz.backend.chat.message.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfo {
    private String fileType;
    private String fileSize;
    private String fileUrl;
    private String fileName;
}