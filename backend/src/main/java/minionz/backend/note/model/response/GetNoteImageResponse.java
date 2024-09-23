package minionz.backend.note.model.response;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNoteImageResponse {
    private Long noteImageId;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}