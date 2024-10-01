package minionz.backend.scrum.workspace;

import lombok.RequiredArgsConstructor;
import minionz.backend.alarm.AlarmService;
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

//        참여 테이블에 참가자들 추가
        workspaceParticipationRepository.save(WorkspaceParticipation.builder().workspace(workspace).user(user).isManager(true).isValid(true).build());
        workspaceParticipationRepository.save(WorkspaceParticipation.builder().workspace(workspace).user(user).isManager(false).isValid(true).build());
//        alarmService.sendWorkspaceEventsToClients(request.getParticipants(),user.getUserId(),1L, workspace.getWorkspaceId());
        request.getParticipants().forEach(participantId ->
                workspaceParticipationRepository.save(WorkspaceParticipation.builder().workspace(workspace).user(User.builder().userId(participantId).build()).isManager(false).isValid(false).build())
        );

//        TODO: request.getParticipants() 에 요청 보내야 함. -> 얘네가 수락하면 workspaceParticipation에 user 추가.


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
