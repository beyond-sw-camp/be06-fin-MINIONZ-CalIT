package minionz.backend.scrum.task.model.response;

import lombok.*;
import minionz.backend.scrum.sprint.model.response.Label;
import minionz.backend.scrum.sprint.model.response.Participant;
import minionz.backend.scrum.task.model.TaskLevel;
import minionz.backend.scrum.task.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadTaskResponse {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime doneDate;
    private List<Participant> participants;
    private TaskStatus status;
    private List<Label> labels;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private TaskLevel difficulty;
    private TaskLevel priority;
}
