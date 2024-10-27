package minionz.apiserver.scrum.label.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadMeetingLabelResponse {
    private Long labelId;
    private String labelName;
    private String description;
    private Integer color;
}
