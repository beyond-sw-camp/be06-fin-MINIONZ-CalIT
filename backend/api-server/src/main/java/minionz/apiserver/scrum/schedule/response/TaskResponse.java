package minionz.apiserver.scrum.schedule.response;

import lombok.Builder;
import lombok.Getter;
import minionz.apiserver.scrum.sprint.model.response.Label;
import minionz.common.scrum.task.model.TaskLevel;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class TaskResponse {
    private Long id;
    private String title;
    private LocalDateTime endDate;
    private TaskLevel priority;
    private List<Label> labels;
}
