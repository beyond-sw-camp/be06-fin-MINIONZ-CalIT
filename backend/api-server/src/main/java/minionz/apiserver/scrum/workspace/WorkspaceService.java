package minionz.apiserver.scrum.workspace;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import minionz.apiserver.alarm.AlarmService;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.workspace.model.request.CreateWorkspaceRequest;
import minionz.apiserver.scrum.workspace.model.response.ReadWorkspaceResponse;
import minionz.apiserver.scrum.workspace_participation.WorkspaceParticipationRepository;
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

    @Transactional
    public void create(User user, CreateWorkspaceRequest request) throws JsonProcessingException {
        Workspace workspace = workspaceRepository.save(Workspace.builder().workspaceName(request.getWorkspaceName()).avatar((int) Math.random() * 12 + 1).build());

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
