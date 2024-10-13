package minionz.apiserver.scrum.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.alarm.AlarmService;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.label_select.TaskLabelSelectRepository;
import minionz.apiserver.scrum.workspace.WorkspaceRepository;
import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.label_select.model.TaskLabelSelect;
import minionz.apiserver.scrum.sprint.model.response.Label;
import minionz.apiserver.scrum.sprint.model.response.Participant;
import minionz.apiserver.scrum.sprint_participation.SprintParticipationRepository;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.SprintRepository;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.common.scrum.sprint_participation.model.SprintParticipation;
import minionz.apiserver.scrum.task.model.request.CreateTaskRequest;
import minionz.apiserver.scrum.task.model.request.UpdateTaskStatusRequest;
import minionz.apiserver.scrum.task.model.response.ReadAllTaskResponse;
import minionz.apiserver.scrum.task.model.response.ReadTaskResponse;
import minionz.apiserver.scrum.task_participation.TaskParticipationRepository;
import minionz.common.scrum.task.TaskRepository;
import minionz.common.scrum.task.model.TaskStatus;
import minionz.common.scrum.task_participation.model.TaskParticipation;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TaskLabelSelectRepository taskLabelSelectRepository;
    private final SprintParticipationRepository sprintParticipationRepository;
    private final TaskParticipationRepository taskParticipationRepository;
    private final SprintRepository sprintRepository;
    private final AlarmService alarmService;

    @Transactional
    public void createTask(User user, CreateTaskRequest request) throws JsonProcessingException {

        Meeting meeting = request.getMeetingId() != null ? Meeting.builder().meetingId(request.getMeetingId()).build() : null;

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
                .meeting(meeting)
                .build());


        SprintParticipation sprintParticipation = sprintParticipationRepository.findBySprintAndUser(Sprint.builder().sprintId(request.getSprintId()).build(), user);
        alarmService.sendEventsToClients(request.getParticipants(),user.getUserId(),3L, task.getTaskId() );

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
        int num = taskRepository.findTaskCount(sprintId) + 1;
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


    public List<ReadAllTaskResponse> readAllTask(Long sprintId) {
        List<Task> result = taskRepository.findAllBySprintSprintId(sprintId);

        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        String workspaceName = sprint.get().getWorkspace().getWorkspaceName();

        List<ReadAllTaskResponse> response = result.stream().map(
                task -> ReadAllTaskResponse
                        .builder()
                        .id(task.getTaskId())
                        .status(task.getStatus())
                        .title(task.getTaskTitle())
                        .labels(findLabels(task))
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .taskNumber(task.getTaskNumber())
                        .participants(findParticipants(task))
                        .priority(task.getPriority())
                        .workspaceName(workspaceName)
                        .build()
        ).toList();

        return response;
    }
    public List<ReadAllTaskResponse> readAllWorkspaceTask(Long workspaceId) {
        List<Task> result = taskRepository.findAllByWorkspaceWorkspaceId(workspaceId);


        List<ReadAllTaskResponse> response = result.stream().map(
                task -> ReadAllTaskResponse
                        .builder()
                        .id(task.getTaskId())
                        .status(task.getStatus())
                        .title(task.getTaskTitle())
                        .labels(findLabels(task))
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .taskNumber(task.getTaskNumber())
                        .participants(findParticipants(task))
                        .priority(task.getPriority())

                        .build()
        ).toList();

        return response;
    }

    public List<ReadAllTaskResponse> readAllMyTask(User user) {

        List<Task> result = taskRepository.findMyTask(user.getUserId());

        List<ReadAllTaskResponse> response = result.stream().map(
                task -> ReadAllTaskResponse
                        .builder()
                        .id(task.getTaskId())
                        .status(task.getStatus())
                        .title(task.getTaskTitle())
                        .labels(findLabels(task))
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .taskNumber(task.getTaskNumber())
                        .participants(findParticipants(task))
                        .priority(task.getPriority())
                        .workspaceName(task.getSprint().getWorkspace().getWorkspaceName())
                        .build()
        ).toList();

        return response;
    }


    public void updateTaskStatus(Long taskId, UpdateTaskStatusRequest request) throws BaseException {
        Optional<Task> result = taskRepository.findById(taskId);

        if (result.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS);
        }

        Task task = result.get();

        if (task.getStatus() == request.getStatus()) {
            throw new BaseException(BaseResponseStatus.UNCHANGED);
        }


        taskRepository.save(
                Task
                        .builder()
                        .taskId(task.getTaskId())
                        .taskTitle(task.taskTitle)
                        .taskContents(task.getTaskContents())
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .doneDate(request.getStatus() == TaskStatus.DONE ? LocalDateTime.now() : null)
                        .difficultly(task.getDifficultly())
                        .priority(task.getPriority())
                        .status(request.getStatus())
                        .taskNumber(task.getTaskNumber())
                        .sprint(task.getSprint())
                        .meeting(task.getMeeting())
                        .build()
        );
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
