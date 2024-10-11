package minionz.apiserver.note.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoteRequest {
    private String noteContent;
}
