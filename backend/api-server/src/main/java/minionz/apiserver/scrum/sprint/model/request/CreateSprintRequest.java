package minionz.apiserver.scrum.sprint.model.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CreateSprintRequest {
    private Long workspaceId;
    private String sprintTitle;
    private String sprintContents;
    private List<Long> labels;
    private List<Long> participants;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
