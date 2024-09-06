package minionz.backend.scrum.label;

import lombok.RequiredArgsConstructor;

import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.SprintLabel;
import minionz.backend.scrum.label.model.TaskLabel;
import minionz.backend.scrum.label.model.request.CreateLabelRequest;
import minionz.backend.scrum.workspace.WorkspaceRepository;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LabelService {
    private final SprintLabelRepository sprintLabelRepository;
    private final TaskLabelRepository taskLabelRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;

    public void createSprintLabel(User user,CreateLabelRequest request) throws BaseException {
        workspaceRepository.findById(request.getWorkspaceId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS)
        );

      if( workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(request.getWorkspaceId(), user.getUserId()) == null){
          throw new BaseException(BaseResponseStatus.INVALID_ACCESS);
      };

        if (sprintLabelRepository.findByLabelName(request.getLabelName()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }

        sprintLabelRepository.save(SprintLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }

    public void createTaskLabel(User user, CreateLabelRequest request) throws BaseException {
        workspaceRepository.findById(request.getWorkspaceId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.WORKSPACE_NOT_EXISTS)
        );

        if( workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(request.getWorkspaceId(), user.getUserId()) == null){
            throw new BaseException(BaseResponseStatus.INVALID_ACCESS);
        };

        if (taskLabelRepository.findByLabelName(request.getLabelName()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }



        taskLabelRepository.save(TaskLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }
}
