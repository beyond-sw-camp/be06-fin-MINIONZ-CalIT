package minionz.backend.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.backend.scrum.task.model.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class BurnTaskResponse {
    private Long id;
    private TaskStatus status;
    private LocalDateTime doneDate;
}
