package minionz.backend.note.model.response;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoteResponse {
    private Long meetingId;
    private String noteTitle;
    private String noteContent;
}
