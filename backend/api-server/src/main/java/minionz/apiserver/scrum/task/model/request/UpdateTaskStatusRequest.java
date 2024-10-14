package minionz.apiserver.scrum.task.model.request;

import lombok.Getter;
import minionz.common.scrum.task.model.TaskStatus;


@Getter
public class UpdateTaskStatusRequest {
    private TaskStatus status;
}
