package minionz.backend.scrum.task.model.request;

import lombok.Getter;
import minionz.backend.scrum.task.model.TaskStatus;


@Getter
public class UpdateTaskStatusRequest {
    private TaskStatus status;
}
