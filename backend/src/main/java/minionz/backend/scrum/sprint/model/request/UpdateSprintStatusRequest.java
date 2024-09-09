package minionz.backend.scrum.sprint.model.request;

import lombok.Getter;
import minionz.backend.scrum.sprint.model.SprintStatus;

@Getter
public class UpdateSprintStatusRequest {
    private SprintStatus status;
}
