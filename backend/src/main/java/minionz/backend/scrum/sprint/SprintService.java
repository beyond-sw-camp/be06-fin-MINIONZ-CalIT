package minionz.backend.scrum.sprint;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.SprintLabel;
import minionz.backend.scrum.label_select.SprintLabelSelectRepository;
import minionz.backend.scrum.label_select.model.SprintLabelSelect;
import minionz.backend.scrum.sprint.model.Sprint;
import minionz.backend.scrum.sprint.model.SprintStatus;
import minionz.backend.scrum.sprint.model.request.CreateSprintRequest;
import minionz.backend.scrum.sprint.model.request.UpdateSprintStatusRequest;
import minionz.backend.scrum.sprint.model.response.Label;
import minionz.backend.scrum.sprint.model.response.Participant;
import minionz.backend.scrum.sprint.model.response.ReadAllSprintResponse;
import minionz.backend.scrum.sprint.model.response.ReadSprintResponse;
import minionz.backend.scrum.sprint_participation.SprintParticipationRepository;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import minionz.backend.scrum.task.TaskRepository;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.task.model.TaskStatus;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final SprintParticipationRepository sprintParticipationRepository;
    private final SprintLabelSelectRepository sprintLabelSelectRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;  // final이 없으면 초기화 안됨. RequiredArgsConstructor로 의존성 주입 안됨. like const
    private final TaskRepository taskRepository;

    public void createSprint(User user, CreateSprintRequest request) throws BaseException {
        Sprint sprint = sprintRepository.save(Sprint
                .builder()
                .sprintTitle(request.getSprintTitle())
                .sprintContents(request.getSprintContents())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(SprintStatus.TODO)
                .workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build())
                .build());

//        TODO: 알람 보내야합니다!
        sprintParticipationRepository.save(SprintParticipation.builder().sprint(sprint).user(user).isManager(true).build());
        sprintParticipationRepository.save(SprintParticipation.builder().sprint(sprint).user(user).isManager(false).build());
        request.getParticipants().forEach(participantId ->
                sprintParticipationRepository.save(SprintParticipation
                        .builder()
                        .sprint(sprint)
                        .user(User.builder().userId(participantId).build())
                        .isManager(false)
                        .build())
        );

//      TODO: 스프린트 라벨이 존재하는지 검증하는 유효성 테스트 필요
        request.getLabels().forEach(labelId ->
                sprintLabelSelectRepository.save(SprintLabelSelect
                        .builder()
                        .sprintLabel(SprintLabel.builder().sprintLabelId(labelId).build())
                        .sprint(sprint)
                        .build()));
    }

    public ReadSprintResponse readSprint(User user, Long id) {
        Optional<Sprint> result = sprintRepository.findById(id);

        if (result.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS);
        }

        Sprint sprint = result.get();

        List<Participant> participants = findParticipants(sprint);

        Boolean isManager = participants.stream()
                .anyMatch(participant -> participant.getId().equals(user.getUserId()) && participant.getIsManager());

        return ReadSprintResponse
                .builder()
                .sprintId(sprint.getSprintId())
                .title(sprint.getSprintTitle())
                .contents(sprint.getSprintContents())
                .labels(findLabels(sprint))
                .status(sprint.getStatus())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .participants(participants)
                .isManager(isManager)
                .build();
    }

    public List<ReadAllSprintResponse> readAllSprint(User user, Long id) throws BaseException {
        List<Sprint> result = sprintRepository.findAllByWorkspaceWorkspaceId(id);

        List<ReadAllSprintResponse> response = result.stream().map(
                sprint -> ReadAllSprintResponse
                        .builder()
                        .sprintId(sprint.getSprintId())
                        .title(sprint.getSprintTitle())
                        .labels(findLabels(sprint))
                        .status(sprint.getStatus())
                        .startDate(sprint.getStartDate())
                        .endDate(sprint.getEndDate())
                        .manager(Participant
                                .builder()
                                .id(sprintParticipationRepository.findBySprintAndIsManager(sprint, true).getUser().getUserId())
                                .userName(sprintParticipationRepository.findBySprintAndIsManager(sprint, true).getUser().getUserName())
                                .isManager(true)
                                .build())

                        .build()
        ).toList();

        return response;
    }

    public void updateSprintStatus(Long sprintId, UpdateSprintStatusRequest request) throws BaseException {
        Optional<Sprint> result = sprintRepository.findById(sprintId);

        if (result.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS);
        }

        Sprint sprint = result.get();

        if (sprint.getStatus() == request.getStatus()) {
            throw new BaseException(BaseResponseStatus.UNCHANGED);
        }

        if (request.getStatus() == SprintStatus.DONE) {
            sprint.getTasks().forEach(
                    task ->
                            taskRepository.save(
                                    Task
                                            .builder()
                                            .taskId(task.getTaskId())
                                            .taskTitle(task.getTaskTitle())
                                            .taskContents(task.getTaskContents())
                                            .startDate(task.getStartDate())
                                            .endDate(task.getEndDate())
                                            .doneDate(task.getStatus() == TaskStatus.DONE ? task.getDoneDate() : LocalDateTime.now())
                                            .difficultly(task.getDifficultly())
                                            .priority(task.getPriority())
                                            .status(TaskStatus.DONE)
                                            .taskNumber(task.getTaskNumber())
                                            .sprint(task.getSprint())
                                            .meeting(task.getMeeting())
                                            .build()
                            )
            );
        }

        sprintRepository.save(
                Sprint
                        .builder()
                        .sprintId(sprint.getSprintId())
                        .sprintTitle(sprint.getSprintTitle())
                        .sprintContents(sprint.getSprintContents())
                        .startDate(sprint.getStartDate())
                        .endDate(sprint.getEndDate())
                        .status(request.getStatus())
                        .workspace(sprint.getWorkspace())
                        .build()
        );

    }

    public List<Label> findLabels(Sprint sprint) {
        return sprint.getSprintLabelSelects().stream().map(
                label -> Label
                        .builder()
                        .id(label.getSprintLabel().getSprintLabelId())
                        .labelName(label.getSprintLabel().getLabelName())
                        .color(label.getSprintLabel().getColor())
                        .build()
        ).toList();
    }

    public List<Participant> findParticipants(Sprint sprint) {
        return sprint.getSprintParticipations().stream().map(
                participant -> Participant.builder()
                        .id(participant.getUser().getUserId())
                        .userName(participant.getUser().getUserName())
                        .isManager(participant.getIsManager())
                        .build()
        ).toList();
    }

}
