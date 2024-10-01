package minionz.backend.scrum.workspace;

import lombok.RequiredArgsConstructor;
import minionz.backend.alarm.AlarmService;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.backend.scrum.workspace.model.response.ReadWorkspaceResponse;
import minionz.backend.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.backend.scrum.workspace_participation.model.WorkspaceParticipation;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;
    private final AlarmService alarmService;

    @Transactional
    public void create(User user, CreateWorkspaceRequest request) {
        Workspace workspace = workspaceRepository.save(Workspace.builder().workspaceName(request.getWorkspaceName()).build());

        workspaceParticipationRepository.save(WorkspaceParticipation.builder().workspace(workspace).user(user).isManager(true).isValid(true).build());
        workspaceParticipationRepository.save(WorkspaceParticipation.builder().workspace(workspace).user(user).isManager(false).isValid(true).build());
        alarmService.sendEventsToClients(request.getParticipants(), user.getUserId(), 1L, workspace.getWorkspaceId());
        request.getParticipants().forEach(participantId ->
                workspaceParticipationRepository.save(WorkspaceParticipation.builder().workspace(workspace).user(User.builder().userId(participantId).build()).isManager(false).isValid(false).build())
        );

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
