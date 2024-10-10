package minionz.apiserver.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.note.NoteRepository;
import minionz.apiserver.scrum.label.model.request.CreateLabelRequest;
import minionz.apiserver.scrum.label.model.request.CreateMeetingLabelRequest;
import minionz.apiserver.scrum.label.model.response.ReadLabelResponse;
import minionz.apiserver.scrum.label.model.response.ReadMeetingLabelResponse;
import minionz.common.scrum.label.model.NoteLabel;
import minionz.common.scrum.label.model.SprintLabel;
import minionz.common.scrum.label.model.TaskLabel;
import minionz.common.scrum.workspace.model.Workspace;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LabelService {
    private final SprintLabelRepository sprintLabelRepository;
    private final TaskLabelRepository taskLabelRepository;
    private final NoteRepository noteRepository;
    private final NoteLabelRepository noteLabelRepository;


    public void createSprintLabel(CreateLabelRequest request) throws BaseException {
        if (sprintLabelRepository.findByLabelNameAndWorkspace(request.getLabelName(), Workspace.builder().workspaceId(request.getWorkspaceId()).build()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }

        sprintLabelRepository.save(SprintLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }

    public List<ReadLabelResponse> readSprintLabel(Long id) throws BaseException {
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

    public void createTaskLabel(CreateLabelRequest request) throws BaseException {
        if (taskLabelRepository.findByLabelNameAndWorkspace(request.getLabelName(), Workspace.builder().workspaceId(request.getWorkspaceId()).build()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }

        taskLabelRepository.save(TaskLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }

    public void createNoteLabel(CreateMeetingLabelRequest request) throws BaseException {
        if(noteLabelRepository.findByLabelNameAndWorkspace(request.getLabelName(), Workspace.builder().workspaceId(request.getWorkspaceId()).build()) != null) {
            throw new BaseException(BaseResponseStatus.LABEL_ALREADY_EXISTS);
        }
        noteLabelRepository.save(NoteLabel.builder().workspace(Workspace.builder().workspaceId(request.getWorkspaceId()).build()).labelName(request.getLabelName()).description(request.getDescription()).color(request.getColor()).build());
    }

    public List<ReadLabelResponse> readTaskLabel(Long id) throws BaseException {

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

    public List<ReadMeetingLabelResponse> readNoteLabel(Long id) throws BaseException{

        List<NoteLabel> labels = noteLabelRepository.findByWorkspaceWorkspaceId(id);

        List<ReadMeetingLabelResponse> response = labels.stream().map(
                label -> ReadMeetingLabelResponse
                        .builder()
                        .labelId(label.getNoteLabelId())
                        .labelName(label.getLabelName())
                        .description(label.getDescription())
                        .color(label.getColor())
                        .build()).toList();
        return response;
    }



}
