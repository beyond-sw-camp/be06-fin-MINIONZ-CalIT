package minionz.backend.scrum.sprint.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadSprintResponse {
    private Long sprintId;
    private String contents;
    private List<Label> labels;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Participant> participants;
    private Boolean isManager;
}
