package minionz.apiserver.scrum.label;

import lombok.RequiredArgsConstructor;
import minionz.apiserver.common.exception.BaseException;
import minionz.apiserver.common.responses.BaseResponse;
import minionz.apiserver.common.responses.BaseResponseStatus;
import minionz.apiserver.scrum.label.model.request.CreateLabelRequest;
import minionz.apiserver.scrum.label.model.request.CreateMeetingLabelRequest;
import minionz.apiserver.scrum.label.model.response.ReadLabelResponse;
import minionz.apiserver.scrum.label.model.response.ReadMeetingLabelResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/label")
public class LabelController {
    private final LabelService labelService;

    @PostMapping("/{workspaceId}/sprint")
    public BaseResponse<BaseResponseStatus> createSprintLabel(@PathVariable Long workspaceId, @RequestBody CreateLabelRequest request) {

        try {
            labelService.createSprintLabel(request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_CREATE_SUCCESS);
    }

    @GetMapping("/{workspaceId}/sprint")
    public BaseResponse<List<ReadLabelResponse>> readSprintLabel(@PathVariable Long workspaceId) {
        List<ReadLabelResponse> response;

        try {
            response = labelService.readSprintLabel(workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }

    @PostMapping("/{workspaceId}/task")
    public BaseResponse<BaseResponseStatus> createTaskLabel(@PathVariable Long workspaceId, @RequestBody CreateLabelRequest request) {

        try {
            labelService.createTaskLabel(request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.TASK_LABEL_CREATE_SUCCESS);
    }

    @GetMapping("/{workspaceId}/task")
    public BaseResponse<List<ReadLabelResponse>> readTaskLabel(@PathVariable Long workspaceId) {
        List<ReadLabelResponse> response;

        try {
            response = labelService.readTaskLabel(workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

        return new BaseResponse<>(BaseResponseStatus.SPRINT_LABEL_READ_SUCCESS, response);
    }

    @PostMapping("/{workspaceId}/note")
    public BaseResponse<BaseResponseStatus> createNoteLabel(@PathVariable Long workspaceId, @RequestBody CreateMeetingLabelRequest request) {

        try {
            labelService.createNoteLabel(request);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        return new BaseResponse<>(BaseResponseStatus.NOTE_LABEL_CREATE_SUCCESS);

    }

    @GetMapping("/{workspaceId}/note")
    public BaseResponse<List<ReadMeetingLabelResponse>> readNoteLabel(@PathVariable Long workspaceId) {

        List<ReadMeetingLabelResponse> response = new ArrayList<>();

        try {
            response = labelService.readNoteLabel(workspaceId);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
        return new BaseResponse<>(BaseResponseStatus.NOTE_LABEL_SEARCH_SUCCESS, response);
    }

}
