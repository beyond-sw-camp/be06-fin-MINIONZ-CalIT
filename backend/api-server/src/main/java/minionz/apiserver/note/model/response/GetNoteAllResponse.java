package minionz.apiserver.note.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNoteAllResponse {
    private Long meetingId;
    private String meetingTitle;
    private String label;
    private String noteContent;
}