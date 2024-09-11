package minionz.backend.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.scrum.task.model.TaskLevel;

import java.time.LocalDateTime;

@Getter
@Builder
public class PriorityTaskResponse {
    private Long id;
    private String title;
    private TaskLevel priority;
    private String workspaceName;
    private LocalDateTime endDate;
    private String taskNumber;
}
