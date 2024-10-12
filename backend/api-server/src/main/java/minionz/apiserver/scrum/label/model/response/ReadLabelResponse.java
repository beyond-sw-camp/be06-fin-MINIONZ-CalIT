package minionz.apiserver.scrum.label.model.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReadLabelResponse {
    private Long labelId;
    private String labelName;
    private String description;
    private String color;
}
