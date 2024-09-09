package minionz.backend.scrum.task.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.scrum.sprint.model.response.Label;
import minionz.backend.scrum.task.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadAllTaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;
    private List<Label> labels;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String taskNumber;
}
