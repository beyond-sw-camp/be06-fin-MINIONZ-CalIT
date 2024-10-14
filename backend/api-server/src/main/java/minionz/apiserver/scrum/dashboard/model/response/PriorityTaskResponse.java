package minionz.apiserver.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.common.scrum.task.model.TaskLevel;

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
