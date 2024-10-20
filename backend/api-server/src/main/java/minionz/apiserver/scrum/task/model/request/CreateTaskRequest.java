package minionz.apiserver.scrum.task.model.request;

import lombok.Builder;
import lombok.Getter;
import minionz.common.scrum.task.model.TaskLevel;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CreateTaskRequest {
    private Long sprintId;
    private String title;
    private String contents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private TaskLevel difficulty;
    private TaskLevel priority;
    private List<Long> labels;
    private List<Long> participants;
}
