package minionz.backend.scrum.task;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.SprintLabel;
import minionz.backend.scrum.label.model.TaskLabel;
import minionz.backend.scrum.label_select.TaskLabelSelectRepository;
import minionz.backend.scrum.label_select.model.SprintLabelSelect;
import minionz.backend.scrum.label_select.model.TaskLabelSelect;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.sprint.model.response.Label;
import minionz.backend.scrum.sprint.model.response.Participant;
import minionz.backend.scrum.sprint_participation.SprintParticipationRepository;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.task.model.TaskStatus;
import minionz.backend.scrum.task.model.request.CreateTaskRequest;
import minionz.backend.scrum.task.model.response.ReadTaskResponse;
import minionz.backend.scrum.task_participation.TaskParticipationRepository;
import minionz.backend.scrum.task_participation.model.TaskParticipation;
import minionz.backend.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskLabelSelectRepository taskLabelSelectRepository;
    private final SprintParticipationRepository sprintParticipationRepository;
    private final TaskParticipationRepository taskParticipationRepository;

    @Transactional
    public void createTask(User user, CreateTaskRequest request) {
        Task task = taskRepository.save(Task
                .builder()
                .sprint(Sprint.builder().sprintId(request.getSprintId()).build())
                .taskTitle(request.getTitle())
                .taskContents(request.getContents())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .difficultly(request.getDifficulty())
                .priority(request.getPriority())
                .status(TaskStatus.NO_STATUS)
                .taskNumber(makeTaskNumber(request.getSprintId()))
                .build());

//        TODO: spint에 참여 중인 유저인지에 대한 검증이 필요함.
//        참여 테이블에 참가자들 추가
        SprintParticipation sprintParticipation = sprintParticipationRepository.findBySprintAndUser(Sprint.builder().sprintId(request.getSprintId()).build(), user);
        if (sprintParticipation.getIsManager()) {
            request.getParticipants().forEach(participantId ->
                    taskParticipationRepository.save(TaskParticipation
                            .builder()
                            .task(task)
                            .user(User.builder().userId(participantId).build())
                            .build())
            );
        } else {
            throw new BaseException(BaseResponseStatus.TASK_LABEL_SELECT_FAIL);
        }

        //      TODO: 태스크 라벨이 존재하는지 검증하는 유효성 테스트 필요
        request.getLabels().forEach(labelId ->
                taskLabelSelectRepository.save(TaskLabelSelect
                        .builder()
                        .taskLabel(TaskLabel.builder().taskLabelId(labelId).build())
                        .task(task)
                        .build()));
    }

    public String makeTaskNumber(Long sprintId) {
        int num = taskRepository.findTaskCount(sprintId);
        return String.format("%03d", num);
    }

    public ReadTaskResponse readTask(Long taskId) {
        Optional<Task> result = taskRepository.findById(taskId);

        if (result.isEmpty()) {
            throw new BaseException(BaseResponseStatus.TASK_NOT_EXISTS);
        }

        Task task = result.get();

        return ReadTaskResponse
                .builder()
                .id(task.getTaskId())
                .title(task.getTaskTitle())
                .contents(task.getTaskContents())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .doneDate(task.getDoneDate())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .modifiedAt(task.getModifiedAt())
                .difficulty(task.getDifficultly())
                .priority(task.getPriority())
                .labels(findLabels(task))
                .participants(findParticipants(task))
                .build();
    }


    public List<Label> findLabels(Task task) {
        return task.getTaskLabelSelects().stream().map(
                label -> Label
                        .builder()
                        .id(label.getTaskLabel().getTaskLabelId())
                        .labelName(label.getTaskLabel().getLabelName())
                        .color(label.getTaskLabel().getColor())
                        .build()
        ).toList();
    }

    public List<Participant> findParticipants(Task task) {
        return task.getTaskParticipations().stream().map(
                participant -> Participant.builder()
                        .id(participant.getUser().getUserId())
                        .userName(participant.getUser().getUserName())
                        .isManager(true)
                        .build()
        ).toList();
    }
}