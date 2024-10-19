package minionz.apiserver.scrum.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.alarm.AlarmService;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.label_select.TaskLabelSelectRepository;
import minionz.common.scrum.label_select.model.TaskLabelSelect;
import minionz.apiserver.scrum.sprint.model.response.Label;
import minionz.apiserver.scrum.sprint.model.response.Participant;
import minionz.common.scrum.meeting.model.Meeting;
import minionz.common.scrum.sprint.repository.SprintRepository;
import minionz.common.scrum.sprint.model.Sprint;
import minionz.apiserver.scrum.task.model.request.CreateTaskRequest;
import minionz.apiserver.scrum.task.model.request.UpdateTaskStatusRequest;
import minionz.apiserver.scrum.task.model.response.ReadAllTaskResponse;
import minionz.apiserver.scrum.task.model.response.ReadTaskResponse;
import minionz.common.scrum.task.repository.TaskRepository;
import minionz.common.scrum.task.model.TaskStatus;
import minionz.common.scrum.task.model.Task;
import minionz.common.scrum.task_participation.TaskParticipationRepository;
import minionz.common.scrum.task_participation.model.TaskParticipation;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskLabelSelectRepository taskLabelSelectRepository;
    private final TaskParticipationRepository taskParticipationRepository;
    //    private final SprintParticipationRepository sprintParticipationRepository;
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

        alarmService.sendEventsToClients(request.getParticipants(), user.getUserId(), 3L, task.getTaskId());

        List<Long> labels = request.getLabels();
        List<Long> participants = request.getParticipants();

        if (labels == null) {
            labels = new ArrayList<>();
        }
        if (participants == null) {
            participants = new ArrayList<>();
        }

        participants.forEach(participantId -> {
            taskParticipationRepository.save(TaskParticipation
                    .builder()
                    .task(task)
                    .user(User.builder().userId(participantId).build())
                    .build());
        });

        labels.forEach(labelId ->
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
        List<Task> result = taskRepository.findAllBySprintIdAndUserId(sprintId, null);

        // Optional 조회 및 추가 쿼리 제거
        String workspaceName = result.isEmpty() ? null : result.get(0).getSprint().getWorkspace().getWorkspaceName();

        List<ReadAllTaskResponse> response = result.stream().map(
                task -> ReadAllTaskResponse
                        .builder()
                        .id(task.getTaskId())
                        .status(task.getStatus())
                        .title(task.getTaskTitle())
                        .labels(findLabels(task))
                        .startDate(task.getStartDate())
                        .endDate(task.getEndDate())
                        .doneDate(task.getDoneDate())
                        .taskNumber(task.getTaskNumber())
                        .participants(findParticipants(task))
                        .priority(task.getPriority())
                        .workspaceName(workspaceName)
                        .build()
        ).toList();

        return response;
    }

    public List<ReadAllTaskResponse> readAllWorkspaceTask(Long workspaceId) {
        List<Task> result = taskRepository.findAllByWorkspaceId(workspaceId);

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

    public List<Map<TaskStatus, List<ReadAllTaskResponse>>> readAllTaskByStatus(Long sprintId, Long userId) {
        // 스프린트 ID로 모든 Task 가져오기
        List<Task> result = taskRepository.findAllBySprintIdAndUserId(sprintId, userId);

        // 스프린트 정보 가져오기
        Optional<Sprint> sprint = sprintRepository.findById(sprintId);
        String workspaceName = sprint.get().getWorkspace().getWorkspaceName();

        // 상태별로 기본 빈 리스트를 미리 준비
        Map<TaskStatus, List<ReadAllTaskResponse>> groupedByStatus = new HashMap<>();
        groupedByStatus.put(TaskStatus.NO_STATUS, new ArrayList<>());
        groupedByStatus.put(TaskStatus.TODO, new ArrayList<>());
        groupedByStatus.put(TaskStatus.IN_PROGRESS, new ArrayList<>());
        groupedByStatus.put(TaskStatus.DONE, new ArrayList<>());

        // Task들을 ReadAllTaskResponse로 변환하고 상태별로 그룹화
        result.stream()
                .map(task -> ReadAllTaskResponse.builder()
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
                )
                .forEach(response -> {
                    // TaskStatus 타입 그대로 유지
                    TaskStatus statusKey = Optional.ofNullable(response.getStatus()).orElse(TaskStatus.NO_STATUS);
                    groupedByStatus.get(statusKey).add(response); // 상태에 해당하는 리스트에 추가
                });

        // Map을 List로 변환하여 반환
        return groupedByStatus.entrySet().stream()
                .map(entry -> Map.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
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
                        .persona(participant.getUser().getPersona())
                        .isManager(true)
                        .build()
        ).toList();
    }


}
