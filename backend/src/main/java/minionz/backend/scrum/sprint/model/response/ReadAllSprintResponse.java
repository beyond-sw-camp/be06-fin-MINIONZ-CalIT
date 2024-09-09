package minionz.backend.scrum.sprint.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.scrum.sprint.model.SprintStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadAllSprintResponse {
    private Long sprintId;
    private String title;
    private List<Label> labels;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Participant manager;
    private SprintStatus status;
}
