package minionz.apiserver.scrum.dashboard.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.common.scrum.task.model.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class BurnTaskResponse {
    private Long id;
    private TaskStatus status;
    private LocalDateTime doneDate;
}
