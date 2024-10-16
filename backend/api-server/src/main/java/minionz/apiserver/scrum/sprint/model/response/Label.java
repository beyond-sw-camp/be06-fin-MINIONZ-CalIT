package minionz.apiserver.scrum.sprint.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Label {
    private Long id;
    private String labelName;
    private Integer color;
}
