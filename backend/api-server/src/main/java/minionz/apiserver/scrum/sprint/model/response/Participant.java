package minionz.apiserver.scrum.sprint.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Participant {
    private Long id;
    private String userName;
    private Boolean isManager;
    private Integer persona;
}
