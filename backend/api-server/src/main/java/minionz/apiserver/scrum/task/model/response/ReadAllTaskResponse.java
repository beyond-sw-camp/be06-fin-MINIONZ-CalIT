package minionz.apiserver.scrum.task.model.response;

import lombok.Builder;
import lombok.Getter;
import minionz.apiserver.scrum.sprint.model.response.Label;
import minionz.apiserver.scrum.sprint.model.response.Participant;
import minionz.common.scrum.task.model.TaskLevel;
import minionz.common.scrum.task.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReadAllTaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;
    private List<Label> labels;
    private String taskNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime doneDate;
    private List<Participant> participants;
    private TaskLevel priority;
    private String workspaceName;
}
