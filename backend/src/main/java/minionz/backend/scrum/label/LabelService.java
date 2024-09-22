package minionz.backend.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.backend.common.exception.BaseException;
import minionz.backend.common.responses.BaseResponseStatus;
import minionz.backend.scrum.label.model.NoteLabel;
import minionz.backend.scrum.label.model.SprintLabel;
import minionz.backend.scrum.label.model.TaskLabel;
import minionz.backend.scrum.label.model.request.CreateLabelRequest;
import minionz.backend.scrum.label.model.response.ReadLabelResponse;
import minionz.backend.scrum.workspace.WorkspaceRepository;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.scrum.workspace_participation.WorkspaceParticipationRepository;
import minionz.backend.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LabelService {
    private final SprintLabelRepository sprintLabelRepository;
    private final TaskLabelRepository taskLabelRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceParticipationRepository workspaceParticipationRepository;
    private final NoteLabelRepository noteLabelRepository;


    public void createSprintLabel(User user, CreateLabelRequest request) throws BaseException {
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(request.getWorkspaceId(), user.getUserId()).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );

        if (sprintLabelRepository.findByLabelName(request.getLabelName()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }

        sprintLabelRepository.save(SprintLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }
    public void createNoteLabel(User user, CreateLabelRequest request) throws BaseException {
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(request.getWorkspaceId(), user.getUserId()).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );

        if (noteLabelRepository.findByLabelName(request.getLabelName()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }

        noteLabelRepository.save(NoteLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }

    public List<ReadLabelResponse> readSprintLabel(User user, Long id) throws BaseException {
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(id, user.getUserId()).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );

        List<SprintLabel> labels = sprintLabelRepository.findByWorkspaceWorkspaceId(id);

        List<ReadLabelResponse> response = labels.stream().map(
                label -> ReadLabelResponse
                        .builder()
                        .labelId(label.getSprintLabelId())
                        .labelName(label.getLabelName())
                        .description(label.getDescription())
                        .color(label.getColor())
                        .build()).toList();

        return response;
    }
    public List<ReadLabelResponse> readNoteLabel(User user, Long id) throws BaseException {
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(id, user.getUserId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );
        List<NoteLabel> labels = noteLabelRepository.findByWorkspaceWorkspaceId(id);

        List<ReadLabelResponse> response = labels.stream().map(
                label -> ReadLabelResponse.builder()
                        .labelId(label.getNoteLabelId())
                        .labelName(label.getLabelName())
                        .description(label.getDescription())
                        .color(label.getColor())
                        .build()).toList();
        return response;
    }

    public void createTaskLabel(User user, CreateLabelRequest request) throws BaseException {
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(request.getWorkspaceId(), user.getUserId()).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );

        if (taskLabelRepository.findByLabelName(request.getLabelName()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }

        taskLabelRepository.save(TaskLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }

    public List<ReadLabelResponse> readTaskLabel(User user, Long id) throws BaseException {
        workspaceParticipationRepository.findByWorkspaceWorkspaceIdAndUserUserId(id, user.getUserId()).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_ACCESS)
        );

        List<TaskLabel> labels = taskLabelRepository.findByWorkspaceWorkspaceId(id);

        List<ReadLabelResponse> response = labels.stream().map(
                label -> ReadLabelResponse
                        .builder()
                        .labelId(label.getTaskLabelId())
                        .labelName(label.getLabelName())
                        .description(label.getDescription())
                        .color(label.getColor())
                        .build()).toList();

        return response;
    }


}
