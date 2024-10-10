package minionz.apiserver.scrum.sprint.model.request;

import lombok.Getter;
import minionz.common.scrum.sprint.model.SprintStatus;

@Getter
public class UpdateSprintStatusRequest {
    private SprintStatus status;
}
