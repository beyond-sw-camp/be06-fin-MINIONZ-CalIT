package minionz.apiserver.scrum.workspace;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.alarm.AlarmService;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.apiserver.scrum.workspace.model.response.ReadWorkspaceResponse;
import minionz.common.scrum.label.NoteLabelRepository;
import minionz.common.scrum.label.SprintLabelRepository;
import minionz.common.scrum.label.TaskLabelRepository;
import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.label.model.SprintLabel;
import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.workspace.WorkspaceRepository;
import minionz.common.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.common.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.common.scrum.workspace.model.Workspace;
import minionz.common.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;
    private final AlarmService alarmService;
    private final SprintLabelRepository sprintLabelRepository;
    private final TaskLabelRepository taskLabelRepository;
    private final NoteLabelRepository noteLabelRepository;

    private final WorkspaceLabelInitializer workspaceLabelInitializer;

    @Transactional
    public void create(User user, CreateWorkspaceRequest request) throws JsonProcessingException {
        // 워크스페이스 생성
        Workspace workspace = workspaceRepository.save(
                Workspace.builder()
                        .workspaceName(request.getWorkspaceName())
                        .avatar((int) (Math.random() * 12) + 1)
                        .build()
        );

        // 워크스페이스 참여자 저장
        workspaceParticipationRepository.save(WorkspaceParticipation.builder()
                .workspace(workspace)
                .user(user)
                .isManager(true)
                .isValid(true)
                .build());

        workspaceParticipationRepository.save(WorkspaceParticipation.builder()
                .workspace(workspace)
                .user(user)
                .isManager(false)
                .isValid(true)
                .build());

        alarmService.sendEventsToClients(request.getParticipants(), user.getUserId(), 1L, workspace.getWorkspaceId());

        request.getParticipants().forEach(participantId ->
                workspaceParticipationRepository.save(WorkspaceParticipation.builder()
                        .workspace(workspace)
                        .user(User.builder().userId(participantId).build())
                        .isManager(false)
                        .isValid(false)
                        .build())
        );

        // 라벨 초기화
        List<SprintLabel> sprintLabels = workspaceLabelInitializer.createDefaultSprintLabels(workspace);
        List<TaskLabel> taskLabels = workspaceLabelInitializer.createDefaultTaskLabels(workspace);
        List<NoteLabel> noteLabels = workspaceLabelInitializer.createDefaultMeetingLabels(workspace);

        // 생성된 라벨을 저장
        sprintLabelRepository.saveAll(sprintLabels);
        taskLabelRepository.saveAll(taskLabels);
        noteLabelRepository.saveAll(noteLabels);
    }

    @Transactional
    public void accept(User user, Long workspaceId) throws BaseException {
        WorkspaceParticipation workspaceParticipation = workspaceParticipationRepository.findWorkspaceParticipationByWorkspaceAndUserAndIsManager(Workspace.builder().workspaceId(workspaceId).build(), user, false);

        if (workspaceParticipation.getIsValid()) {
            throw new BaseException(BaseResponseStatus.WORKSPACE_INVITE_END);
        }

        workspaceParticipationRepository.save(WorkspaceParticipation
                .builder()
                .workspaceParticipationId(workspaceParticipation.getWorkspaceParticipationId())
                .workspace(Workspace.builder().workspaceId(workspaceId).build())
                .user(user)
                .isValid(true)
                .isManager(workspaceParticipation.getIsManager())
                .build());
    }

    @Transactional
    public void deny(User user, Long workspaceId) throws BaseException {
        WorkspaceParticipation workspaceParticipation = workspaceParticipationRepository.findWorkspaceParticipationByWorkspaceAndUserAndIsManager(Workspace.builder().workspaceId(workspaceId).build(), user, false);

        if (workspaceParticipation.getIsValid()) {
            throw new BaseException(BaseResponseStatus.WORKSPACE_INVITE_END);
        }

        workspaceParticipationRepository.delete(workspaceParticipation);
    }


    public List<ReadWorkspaceResponse> readAll(User user) {
        List<Workspace> workspaces = workspaceRepository.findWorkspaceByUserId(user.getUserId());

        List<ReadWorkspaceResponse> response = workspaces.stream().map(workspace ->
                        ReadWorkspaceResponse.builder()
                                .workspaceId(workspace.getWorkspaceId())
                                .workspaceName(workspace.getWorkspaceName())
                                .avatar(workspace.getAvatar())
                                .build())
                .toList();

        return response;
    }


}


