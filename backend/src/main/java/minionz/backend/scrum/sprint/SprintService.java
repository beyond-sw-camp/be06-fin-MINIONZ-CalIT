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
import minionz.backend.scrum.sprint_participation.SprintParticipationRepository;
import minionz.backend.scrum.sprint_participation.model.SprintParticipation;
import minionz.backend.scrum.workspace.WorkspaceRepository;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final SprintParticipationRepository sprintParticipationRepository;
    private final SprintLabelSelectRepository sprintLabelSelectRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;  // final이 없으면 초기화 안됨. RequiredArgsConstructor로 의존성 주입 안됨. like const

    public void createSprint(User user, CreateSprintRequest request) {
//        user가 워크스페이스 내에 포함됐는지? 확인.
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(request.getWorkspaceId(), user.getUserId()).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );

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
//        참여 테이블에 참가자들 추가
        sprintParticipationRepository.save(SprintParticipation.builder().sprint(sprint).user(user).isManager(true).build());
        request.getParticipants().forEach(participantId ->
                sprintParticipationRepository.save(SprintParticipation
                        .builder()
                        .sprint(sprint)
                        .user(User.builder().userId(participantId).build())
                        .isManager(false)
                        .build())
        );

//        스프린트 라벨이 존재하는지 검증하는 유효성 테스트 필요
        request.getLabels().forEach(labelId ->
                sprintLabelSelectRepository.save(SprintLabelSelect
                        .builder()
                        .sprintLabel(SprintLabel.builder().sprintLabelId(labelId).build())
                        .sprint(sprint)
                        .build()));
    }
}
